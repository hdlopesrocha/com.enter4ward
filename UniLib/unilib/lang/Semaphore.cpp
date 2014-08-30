#ifndef unilib_lang_Semaphore
#define unilib_lang_Semaphore

#include <mutex>
#include <condition_variable>
using namespace std;

class Semaphore{
private:
    mutex mtx;
    condition_variable cv;
    int count;

	public: Semaphore(){
		count=0;
	}
	
	public: void Notify()
    {
        unique_lock<mutex> lck(mtx);
        ++count;
        cv.notify_one();
    }
	
	public: void Wait()
    {
        unique_lock<mutex> lck(mtx);

        while(count == 0){
            cv.wait(lck);
        }
        count--;
    }
};

#endif
