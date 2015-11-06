#include <iostream>
#include <fstream>
#include <cstdint>
#include <vector>
#include <string.h>
#include "http/Server.hpp"
#include <IL/il.h>
#include <IL/ilu.h>

// ffmpeg -f video4linux2 -i /dev/video0 -vf scale=1024:768 -an -qscale:v 2 -f mjpeg http://localhost:1991/stream/test.mjpg
// ffplay http://localhost:1991/stream/test.mjpg

std::map<std::string,std::vector<char>> frameBuffer;
std::mutex frameMutex;

#define STREAM_BLOCK_SIZE 32768

void receiveStream(http::InputStream &in, http::Request &request){
	http::Response response(HTTP_1_1, STATUS_OK);
	
	std::vector<char> frame;	
	while(true){
		std::string lenStr;
		if(in.readLine(lenStr)==-1){
			break;
		}
		
		unsigned int len;   
    	std::stringstream ss;
    	ss << std::hex << lenStr;
    	ss >> len;

		//std::cout << len << std::endl;
 	
		for (int i = 0; i < len; ++i){
			frame.push_back(in.readByte());
		}
	
    	if(len<STREAM_BLOCK_SIZE) {
    		frameMutex.lock();
    		frameBuffer[request.file] = frame;
    		frameMutex.unlock();
   			frame.clear();
	   	}

		if(in.readLine(lenStr)==-1){
			break;
		}
	}
}

void sendStream(http::OutputStream &out, http::Request &request){
	http::Response response(HTTP_1_1, STATUS_OK);
	response.setChunked(STREAM_BLOCK_SIZE);
	response.setContentType("image/jpeg");
	response.sendHeaders(out);

	bool valid = true;
	while(valid){
		frameMutex.lock();
		std::vector<char> frame = frameBuffer[request.file];
		frameMutex.unlock();
		response.setContent(&frame[0],frame.size());
		valid = response.sendBody(out);

		usleep(33333);
	}
	response.endChunk(out);
}


void notFound(http::InputStream &in, http::OutputStream &out){
	http::Response response(HTTP_1_1, STATUS_NOT_FOUND);
	std::string content = "<h1>Page not found!</h1>";

	response.setContentType("text/html");
	response.setContent(content.c_str(), content.length());

	response.sendHeaders(out);
	response.sendBody(out);
}


void index(http::InputStream &in, http::OutputStream &out){
	http::Response response(HTTP_1_1, STATUS_OK);
	std::string content = "<h1>Hello World!</h1>";

	content += "Lorem ipsum dolor sit amet, duo ea consul imperdiet dissentias. Causae intellegam mediocritatem id has, munere saperet electram cu quo. Ei paulo malorum praesent sed, sit dicam iriure aperiam in. Sint affert usu ex, ut tantas semper honestatis eum.<br>";
	content += "Praesent temporibus omittantur his ex. Velit dolor mucius id vis, nostrum scripserit reformidans pri an. Dolorem perfecto ad est, usu no utamur vidisse, eam reque causae eu. Id vis appellantur ullamcorper, omnium qualisque eum et, eos duis illum an. Facilisi erroribus no nam, vitae dicant epicuri usu ei, mei no vocent aliquip nusquam.<br>";
	content += "Dicit nemore ut eos, in vim graece electram assentior. Explicari consequat voluptaria cum ea, vix ad detraxit partiendo, et nam convenire gloriatur. Cum quod quas malis ad, eam nibh dicit molestie at. Bonorum officiis adipisci no pro. Cum vidisse recteque te, ridens dicunt mel in.<br>";
	content += "Quando laudem efficiantur sea ei, in error appellantur sea, cum iriure discere appareat no. Ne affert theophrastus mea, qui id prompta voluptatum vituperatoribus. Ex sed semper adolescens, minimum gloriatur no eos. Has justo fugit assueverit at, tollit option pro in, te malis commodo delenit ius. Per vitae ceteros efficiantur ea, no mea summo delicata scripserit, inermis mentitum no mea. Autem graeco quodsi nec ei, ut quo dicant homero propriae, ea vim novum suavitate.<br>";
	content += "No civibus pertinacia moderatius eam, viris eirmod comprehensam te has. Ad qui ponderum adipisci disputando, partem dignissim vis id. Vim ut dictas repudiare, id inani sanctus signiferumque eum, omittam adipiscing ex est. Id usu possim convenire definitionem, an partem utroque sententiae sea. Te putent diceret sed, eu eos decore pertinax, mel cu sumo quot. Cu ius minim elitr, semper eripuit accumsan duo ex, at sit eius harum.<br>";
	content += "Cum noster lobortis et. Sea vide primis recusabo et. Saepe interesset eam cu, id nam ubique lobortis splendide. Legere principes patrioque sit te, ne munere maiorum nec, qui facilisis argumentum ne. Ei nisl pertinax disputationi has, at pri rebum populo detracto.<br>";
	content += "Ea fabulas prodesset persecuti mel, vix cu nostrud menandri. Ad probo interesset usu, quem soluta vivendum pro ex. Nam enim viris et, summo molestie reprehendunt ad his. Cum ut animal lobortis, ea eam purto pericula abhorreant, wisi principes pri in.<br>";
	content += "Cetero conceptam scribentur est an. Quem posse repudiandae vis ut, no dolor iudico civibus mei. Quem esse incorrupte ut sed, eos ei oportere ocurreret. Eos cu liber atomorum. Nam hinc dolorem tibique in, ne iusto fierent vivendum eos.<br>";
	content += "Ad vix ferri labores, et sit commodo sanctus invenire. Eu aliquid reprimique cum, eu velit mandamus quo. In eam tibique sententiae eloquentiam. Quo et everti mediocrem, has id feugait verterem, ex usu petentium iudicabit.<br>";
	content += "In assum elitr vel, ad mea suas nulla, agam tantas reprimique eos in. Cu vix dicunt legimus necessitatibus. Id vim facer epicurei elaboraret, tempor audiam cu mei. Mel ex unum corpora, eos quot justo mnesarchum at. An nonumy inimicus argumentum quo. Eum labore impedit te, vitae accusam at pri, his facer dolorem vivendum no.<br>";
	content += "Aperiri eruditi omnesque et pri, eam at quem option abhorreant. Nec cu unum prompta, an quot mutat comprehensam eam. Vel et oblique alienum consequat, atqui impedit has et, brute habemus te his. Id vix sapientem corrumpit scripserit, sed possit reformidans eu, qui an idque discere. Errem possit ea duo, definiebas eloquentiam usu no. Eu vel phaedrum patrioque.<br>";
	content += "Aeterno vivendo phaedrum vis ut, sea ea omnesque patrioque. Sea te dicant soleat probatus, sed ridens sanctus ei, at pri assum feugiat. Eius ferri vituperatoribus no sit, eos minimum comprehensam ex. Sea ocurreret appellantur te, ei sea apeirian adipisci philosophia. Cu mel sumo meliore eloquentiam, ius velit ullamcorper ne.<br>";
	content += "Eu quo legimus vivendo, his ad homero soluta. Ut quot munere definitiones pri. Ex vel illud ancillae accusata. Utamur intellegat neglegentur has in, duo id error labore, admodum principes sed in. Vel mutat epicuri blandit ei, veri maluisset omittantur per cu.<br>";
	content += "Nam te dico repudiare, munere denique blandit no duo. Nonumy electram has ei. Porro vitae probatus mei id, dictas saperet detraxit vim te. Sed soluta denique salutatus id, vim ea sint percipit consequat. An mea timeam perfecto, ad brute graeci pro. Quis impetus euripidis mea ex.<br>";
	content += "Libris saperet an sit. Cu sit suscipit repudiandae, eum odio paulo prodesset id, usu cu quodsi diceret. Mei ut ullum feugait, tamquam persequeris ei sit. Vel at labore vocent volutpat, no eam scribentur neglegentur. Eu qui mollis utamur.<br>";
	content += "Id sed ornatus maiorum. An vim error libris, his at aliquid gubergren, at eos errem platonem definitionem. At dicat dicit suavitate pri, falli munere at his. Qui populo vocent pericula no.<br>";
	content += "Ea nemore malorum electram quo, debet appetere ad mel, et enim dictas debitis duo. Adhuc verear nam ex. Malorum splendide quaerendum ea nec, no wisi pericula consetetur eum. Noster sensibus principes cu his, vim quot omnium ponderum id. Sed ex tamquam voluptatum. Cu lorem quaerendum vix.<br>";
	content += "Vidit evertitur te his, ea tation nostro facilisis eam, ius magna singulis tacimates eu. Ut aperiam adversarium quo, eum facete discere nonumes in. At sea molestie corrumpit, et eam rebum complectitur. Vocent voluptaria quo eu.<br>";
	content += "Placerat consequat ne mei, est dicta iusto ex. Sed ex laboramus efficiendi, vix iusto scriptorem disputationi ex. Ei eos elit dicit. Vim ne insolens consetetur. Nec pertinacia referrentur ea.<br>";
	content += "Illud rationibus voluptatum te his. Ne invenire consequat pri. Habemus deleniti philosophia ne eam, ut mei lorem dolorum electram, mea an hinc assueverit signiferumque. Vim justo oporteat ne.<br>";
	content += "Mel malis labores in, possim commune similique ne sea, est albucius verterem imperdiet ad. Sea doctus convenire instructior at. Prima atqui tractatos id vel. Vim ne stet quaestio facilisis, ut minim nobis vel. Ne habeo maiestatis quo, in ius conceptam sententiae. Soleat appetere adversarium et vis, ei sed quas dolores, legere mentitum concludaturque vim te. Noluisse deserunt cum et.<br>";
	content += "At pro adversarium philosophia. Mei wisi clita delicata ea. Ut inimicus maiestatis nam, admodum reformidans no qui. Quis pericula appellantur mea ne, est eius nominavi et. Dicat referrentur te per. Ea meis mediocrem has, doming urbanitas te ius. Vero latine ex eum, ut ius nisl everti consequat, pro sonet laoreet eu.<br>";
	content += "Amet assueverit signiferumque eu eos, quo.<br>";

// ERR_CONTENT_LENGTH_MISMATCH
	response.setContentType("text/html");
	response.setChunked(1024);
	response.sendHeaders(out);

	response.setContent(content.c_str(), content.length());
	response.sendBody(out);
	response.endChunk(out);
}

class MyRequestHandler : public http::RequestHandler {
	public: void onRequestArrived(http::InputStream &in, http::OutputStream &out){
		//request.dump();
		http::Request request(in);
		
		if(request.method.compare("GET")==0){
			if(request.file.compare("/")==0){
				index(in,out);
			}
			else if(request.file.substr(0,7).compare("/stream")==0){
				sendStream(out,request);
			}
			else {
				notFound(in,out);
			}
		} else if(request.method.compare("POST")==0){
			if(request.file.substr(0,7).compare("/stream")==0){
				receiveStream(in,request);
			}
		}
	}
 };

int main () {
 	FILE *file = fopen("test.jpg", "rb");
 	fseek(file, 0, SEEK_END);
	ILuint fileSize = ftell(file);
	char * bytes = (char*) malloc(fileSize);
	fseek(file, 0, SEEK_SET);
	fread(bytes, 1, fileSize, file);
	fclose(file);

	ilLoadL(IL_JPG, bytes, fileSize);
	free(bytes);


	MyRequestHandler handler;
	http::Server server(1991);
	server.run(&handler);
	return 0;
}
