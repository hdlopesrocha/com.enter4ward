
#include "TreeMap.h"

namespace unilib {


	//-------------------------------------------------
	// Node's Class (private)
	//-------------------------------------------------
	class TreeMap::Node {
		public: void * _info;
		public: Node * _left, *_right;
		private: char _height;


		// Node constructor
		public: Node(void * info){
			_info = info;
			_left = _right = NULL;
			_height = 1;	
		}; 

		public: virtual ~Node(){	
		}; 


		float myMax(float a, float b){
			return (a>b)? a : b;
		}

		void setHeight(){
			char l=0,r=0;

			if(_left!=NULL)	l=_left->_height;
			if(_right!=NULL) r=_right->_height;
			_height=myMax(l,r)+1;
		}

		char calculateBalance(){
			char l=0,r=0;
			if(_left!=NULL)		l=_left->_height;
			if(_right!=NULL)	r=_right->_height;
			return r-l;
		}


		public: Node * rotateLeft(){
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

		public: Node* rotateRight(){
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

		public:Node* balance(){
			/* Condicoes AVL */
			Node * node = this;
	
			setHeight();
			
			/* condicoes de rotacAo */
			if(calculateBalance()>1){
				if(_right!=NULL && _right->calculateBalance()<0)
					_right = _right->rotateRight();
				node = rotateLeft();
			}
			else if(calculateBalance()<-1){
				if(_left!=NULL && _left->calculateBalance()>0)
					_left = _left->rotateLeft();
				node = rotateRight();
			}
			setHeight();	
			node->setHeight();	
			

			return node;
		}


		public : Node * put(void * value, Comparator * comparator, void ** replaced){
			int comp = comparator->compare(_info);
			
			if(comp>0){
				_right = _right!=NULL ? _right->put(value, comparator,replaced) : new Node(value);		/* navega para a direita */
			}
			else if(comp<0){
				_left = _left!=NULL ? _left->put(value, comparator,replaced):  new Node(value);		/* navega para a esquerda */
			}
			else {
				*replaced = _info;
				_info = value;
			}
			return balance();
		}

		public: void iterate(void (*func)(void*)){
	

				if(_left!=NULL)	_left->iterate(func);			
				
				func(_info);
				if(_right!=NULL) _right->iterate(func);
			}
	

		public: void clear( void (*func)(void*)){
			if(_left!=NULL)		_left->clear(func);
			if(_right!=NULL)	_right->clear(func);
			if(func!=NULL)
				func(_info);
			delete this;
		}


		public : Node * remove(Comparator * comparator, void ** removed){

				Node * parent=this;
				Node * sucessor = this;

				/* FASE DE PROCURA */
				int comp = comparator->compare(_info);
				if(comp>0 && _right!=NULL)
					_right =_right->remove(comparator,removed);
				else if(comp<0 && _left!=NULL)
					_left = _left->remove(comparator,removed);
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
					*removed = _info;
					delete this;			/* limpa ponteiro para info */		
				}
				
				return sucessor == NULL ? NULL : sucessor->balance();
			}

		
		};

	


	void * TreeMap::remove(Comparator * comparator){
		void * removed=NULL;
		if(_root!=NULL) 
			_root= _root->remove(comparator,&removed);
		return removed;
	}



	void TreeMap::iterate(void (*func)(void*)){
		if(_root!=NULL)
			_root->iterate(func);
	}


	void * TreeMap::put(void * value, Comparator * comparator){
		void * replaced=NULL;
		
		_root = (_root!=NULL) ? _root->put(value, comparator,&replaced) : new Node(value);

		if(replaced==NULL){
		//	++_count;
		}
		return replaced;
	}


	void * TreeMap::get(Comparator * comparator){

		Node * node = _root;
		int comp;
		void * ans = NULL;

		while(node!=NULL){	
			comp = comparator->compare(node->_info);
			if(comp<0)			node = node->_right;		/* navega para a direita */
			else if(comp>0)		node = node->_left;		/* navega para a esquerda */
			else{				ans = node->_info;	break;}	/* node encontrado */
		}
		return ans;
	}

	TreeMap * TreeMap::clear( void (*func)(void*)){
		if(_root!=NULL)
			_root->clear(func);
		return this;
	}

	TreeMap * TreeMap::clear(){
		return clear(NULL);
	}



}


