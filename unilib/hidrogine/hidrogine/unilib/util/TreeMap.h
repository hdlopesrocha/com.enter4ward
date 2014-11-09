#ifndef unilib_util_TreeMap
#define unilib_util_TreeMap

#include <stdlib.h>
#include "Collection.h"
#include "Comparator.h"
#include "Stack.h"
#include "Map.h"
#include <mutex>

namespace unilib {
	template <class K, class V> class TreeMapIterator;

	//-------------------------------------------------
	// Queue's Class
	//-------------------------------------------------
	template <class K, class V> class TreeMap : public Map<K,V>{
		//-------------------------------------------------
		// Node's Class (private)
		//-------------------------------------------------
		public: class Node {
			public: Entry<K,V*> _info;
			public: Node * _left, *_right;
			private: char _height;


			// Node constructor
			public: Node(K key,V * value){
				_info = Entry<K,V*>(key, value);
				_left = _right = NULL;
				_height = 1;	
			}; 

			public: virtual ~Node(){	
			}; 


			float My_max(float a, float b){
				return (a>b)? a : b;
			}

			void SetHeight(){
				char l=0,r=0;

				if(_left!=NULL)	l=_left->_height;
				if(_right!=NULL) r=_right->_height;
				_height=My_max(l,r)+1;
			}

			char CalculateBalance(){
				char l=0,r=0;
				if(_left!=NULL)		l=_left->_height;
				if(_right!=NULL)	r=_right->_height;
				return r-l;
			}

			public: void Clear( void (*func)(Entry<K,V*> ,void*), void * args ){
				if(_left!=NULL)		_left->Clear(func,args);
				if(_right!=NULL)	_right->Clear(func,args);
				if(func!=NULL)
					func(_info,args);
				delete this;
			}

			public: void Delete(){
				if(_left!=NULL)		_left->Delete();
				if(_right!=NULL)	_right->Delete();
		
				delete _info.Value;
				delete this;
			}


			public: Node * RotateLeft(){
				Node * child=NULL;	
				Node * node = this;
				if(node!=NULL){
					child=_right;
					_right=child->_left;
					child->_left= node;
					++child->_height;
					--node->_height;
				}
				return child;
			}

			public: Node* RotateRight(){
				Node * child=NULL;	
				Node * node = this;
				if(node!=NULL){
					child=_left;				
					_left=child->_right;
					child->_right= node;
					++child->_height;
					--node->_height;	
				}
				return child;
			}

			public:Node* Balance(){
				/* Condicoes AVL */
				Node * node = this;
		
				SetHeight();
				
				/* condicoes de rotacAo */
				if(CalculateBalance()>1){
					if(_right!=NULL && _right->CalculateBalance()<0)
						_right = _right->RotateRight();
					node = RotateLeft();
				}
				else if(CalculateBalance()<-1){
					if(_left!=NULL && _left->CalculateBalance()>0)
						_left = _left->RotateLeft();
					node = RotateRight();
				}
				SetHeight();	
				node->SetHeight();	
				

				return node;
			}

			public: void InOrder(void (*func)(V*,void*), void * args){

				if(_left!=NULL)	_left->InOrder(func,args);
				//cout << "#" << _info.GetKey() << endl;
				func(_info.Value, args);
				
				if(_right!=NULL) _right->InOrder(func,args);
			}


			public: void Print(){

				cout << "|";
				if(_left==NULL)		cout<<"  ";
				else				cout<<(_left->_info.Key);

				cout << "| <- |" << (_info.Key) << "| -> |";

				if(_right==NULL)	cout<<"  ";
				else				cout<<(_right->_info.Key);
				cout << "|" << endl;

				if(_left!=NULL)	_left->Print();			
				if(_right!=NULL) _right->Print();
			}


			public: bool ContainsValue(V* value){
				if(_info.Value==value)
					return true;
				if(_left!=NULL && _left->ContainsValue(value))
					return true;
				if(_right!=NULL && _right->ContainsValue(value))
					return true;
				return false;
			}

			public : Node * Put(K key, V * value, Comparator<K> * comparator, V ** replaced){
				int comp = comparator->Compare(_info.Key,key);
				
				if(comp>0){
					_right = _right!=NULL ? _right->Put(key,value, comparator,replaced) : new Node(key,value);		/* navega para a direita */
				}
				else if(comp<0){
					_left = _left!=NULL ? _left->Put(key,value, comparator,replaced):  new Node(key,value);		/* navega para a esquerda */
				}
				else {
					*replaced = _info.Value;
					_info.Value = value;
				}
				return Balance();
			}


			public : Node * Remove(K key, Comparator<K> * comparator, V ** removed){

				Node * parent=this;
				Node * sucessor = this;

				/* FASE DE PROCURA */
				int comp = comparator->Compare(_info.Key,key);
				if(comp>0 && _right!=NULL)
					_right =_right->Remove(key,comparator,removed);
				else if(comp<0 && _left!=NULL)
					_left = _left->Remove(key,comparator,removed);
				else if(comp==0){
					if(_right==NULL){
						/* Nao tem sub-arvores */
						if(_left==NULL){
							sucessor=NULL;	
						}
						/* So tem sub-arvore esquerda*/
						else {
							sucessor=_left;
						}
					}
					else {
						/* So tem sub-arvore direita */
						if(_left==NULL){			
							sucessor=_right;
						}
						/* Tem duas sub-arvores */
						else {
						
							sucessor=_right;
							/* procura o que esta mais a esquerda da sub-arvore direita (o valor sequinte) */
							while(sucessor->_left!=NULL){
								parent=sucessor;
								sucessor=sucessor->_left;
							}
							if(sucessor==_right){
								sucessor->_left=_left;		
							}
							else{
								parent->_left=sucessor->_right;
								sucessor->_left=_left;
								sucessor->_right=_right;
							}

						}
					}
					*removed = _info.Value;
					delete this;			/* limpa ponteiro para info */		
				}
				
				return sucessor == NULL ? NULL : sucessor->Balance();
			}

		
		};

		class TreeMapIterator : public Iterator<Entry<K,V*> >
		{
			private: Node * _node;
			private: Stack<Node*> * _stack;


			public: TreeMapIterator(Node * n){
				_stack = new Stack<Node*>();
				_node = n;
				Next();
			}

			public: virtual ~TreeMapIterator(){
				delete _stack->Clear();
			}

			public: virtual Entry<K,V*> GetValue(){
				return _node->_info;
			}

			public: virtual bool IsValid(){
				return  _node!=NULL;
			}

			public: virtual void Next(){
				while (true){
					if(_node !=  NULL){
						if(_node == _stack->Peek()){
							_node= _stack->Pop();
							_node = _node->_right;
						}else {
							_stack->Push(_node);
							_node = _node->_left; 
						}
					}
					else {
						_node = _stack->Peek();
						break;
					}
				}
			}
		};


		private: Node * _root;	// First node of Queue
		private: int _count;	// Number of nodes
		private: Comparator<K>  * _comparator;
		private: mutex mtx;

		// Queue constructor  
		public: TreeMap<K,V>(Comparator<K> * comparator){
			_root = NULL;
			_comparator = comparator;
			_count = 0;
		}

		public: virtual ~TreeMap<K,V>(){
			Clear();
		}

		public: virtual Map<K,V> * Clear( void (*func)(Entry<K,V*>,void*) , void * args){
			mtx.lock();
			if(_root!=NULL)
				_root->Clear(func, args);
			_count = 0;
			mtx.unlock();
			return this;
		}

		public: virtual Map<K,V> * Delete(){
			mtx.lock();
			if(_root!=NULL){
				_root->Delete();
				_root = NULL;	
			}
			_count = 0;
			mtx.unlock();
			return this;
		}

		public: virtual Entry<K,V*> Leftest(){
			mtx.lock();
			Entry<K,V*> ans;
			for(Node * node = _root; node!=NULL; node = node->_left){	
				ans = node->_info;	
			}
			mtx.unlock();
			return ans;
		}

		public: virtual Map<K,V> * Clear(){
		
			return Clear(NULL,NULL);
		}

		public: virtual V * Put(K key, V * value){
			mtx.lock();
			V * replaced=NULL;
			
			
			_root = (_root!=NULL) ? _root->Put(key,value, _comparator,&replaced) : new Node(key, value);

			if(replaced==NULL){
				++_count;
			}
			mtx.unlock();
			return replaced;
		}

		public: V * Remove(K key){
			mtx.lock();
			V* removed=NULL;
			if(_root!=NULL) 
				_root= _root->Remove(key, _comparator,&removed);
			--_count;
			mtx.unlock();
			return removed;
		}

		public: void Print(){
			if(_root!=NULL)
				_root->Print();
		
		}

		public: virtual V * Get(K key){
			mtx.lock();
			Node * node = _root;
			int comp;
			V* ans = NULL;

			while(node!=NULL){	
				comp = _comparator->Compare(key,node->_info.Key);
				if(comp<0)			node = node->_right;		/* navega para a direita */
				else if(comp>0)		node = node->_left;		/* navega para a esquerda */
				else{				ans = node->_info.Value;	break;}	/* node encontrado */
			}
			mtx.unlock();
			return ans;
		}

		public: virtual bool ContainsKey(K key){
			mtx.lock();
			Node * node = _root;
			int comp;
			bool ans = false;

			while(node!=NULL){	
				comp = _comparator->Compare(key,node->_info.Key);
				if(comp<0)			node = node->_right;		/* navega para a direita */
				else if(comp>0)		node = node->_left;		/* navega para a esquerda */
				else{				ans = true;	break;}			/* node encontrado */
			}
			mtx.unlock();
			return ans;
		}

		public: virtual bool ContainsValue(V * value){
			mtx.lock();
			bool ans =_root!=NULL ?_root->ContainsValue(value) : false; 
			mtx.unlock();
			return ans;
		}

		public: virtual bool Equals(void * o){
			return o==this;
		}

		public: virtual int HashCode(){
			return 0;
		}

		public: virtual bool IsEmpty(){
			return _root == NULL;
		}

		public: virtual void PutAll(Map<K,V> * m){
			// XXX foreach e in m
			// put e here
		}

		public: virtual int Size(){
			return _count;
		}
		
		public: virtual void InOrder(void (*func)(V*,void*), void * args){
			if(_root!=NULL) _root->InOrder(func,args);
		}
		
		public: virtual Collection<V*> * Values(){
			return NULL;		
		};

		public: virtual V** ToArray(){
			return NULL;
		}

		public: virtual TreeMapIterator* GetIterator(){
			return new TreeMapIterator(_root);
		}

	};
}

#endif 

