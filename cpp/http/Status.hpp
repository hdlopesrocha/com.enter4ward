#ifndef HTTP_STATUS_CODE
#define HTTP_STATUS_CODE
#include <string>
#include <vector>


#define HTTP_1_0 "HTTP/1.0"
#define HTTP_1_1 "HTTP/1.1"
#define STATUS_CONTINUE 100
#define STATUS_SWITCHING_PROTOCOLS 101
#define STATUS_OK 200
#define STATUS_CREATED 201 
#define STATUS_ACCEPTED 202
#define STATUS_NON_AUTHORITATIVE_INFORMATION 203
#define STATUS_NO_CONTENT 204
#define STATUS_RESET_CONTENT 205
#define STATUS_PARTIAL_CONTENT 206
#define STATUS_MULTIPLE_CHOICES 300
#define STATUS_MOVED_PERMANENTLY 301
#define STATUS_FOUND 302
#define STATUS_SEE_OTHER 303 
#define STATUS_NOT_MODIFIED 304
#define STATUS_USE_PROXY 305
#define STATUS_TEMPORARY_REDIRECT 307
#define STATUS_BAD_REQUEST 400
#define STATUS_UNAUTHORIZED 401
#define STATUS_PAYMENT_REQUIRED 402
#define STATUS_FORBIDDEN 403
#define STATUS_NOT_FOUND 404
#define STATUS_METHOD_NOT_ALLOWED 405
#define STATUS_NOT_ACCEPTABLE 406
#define STATUS_PROXY_AUTHENTICATION_REQUIRED 407
#define STATUS_REQUEST_TIME_OUT 408
#define STATUS_CONFLICT 409
#define STATUS_GONE 410
#define STATUS_LENGTH_REQUIRED 411
#define STATUS_PRECONDITION_FAILED 412
#define STATUS_REQUEST_ENTITY_TOO_LARGE 413
#define STATUS_REQUEST_URI_TOO_LARGE 414
#define STATUS_UNSUPPORTED_MEDIA_TYPE 415
#define STATUS_REQUESTED_RANGE_NOT_SATISFIABLE 416
#define STATUS_EXPECTATION_FAILED 417
#define STATUS_INTERNAL_SERVER_ERROR 500
#define STATUS_NOT_IMPLEMENTED 501
#define STATUS_BAD_GATEWAY 502
#define STATUS_SERVICE_UNAVAILABLE 503
#define STATUS_GATEWAY_TIME_OUT 504
#define STATUS_HTTP_VERSION_NOT_SUPPORTED 505

namespace http {

	static std::vector<std::string> status_init(){
		std::vector<std::string> vec(1024);
		vec[STATUS_CONTINUE]="Continue";
		vec[STATUS_SWITCHING_PROTOCOLS]="Switching Protocols";
		vec[STATUS_OK]="OK";
		vec[STATUS_CREATED]="Created";
		vec[STATUS_ACCEPTED]="Accepted";
		vec[STATUS_NON_AUTHORITATIVE_INFORMATION]="Non-Authoritative Information";
		vec[STATUS_NO_CONTENT]="No Content";
		vec[STATUS_RESET_CONTENT]="Reset Content";
		vec[STATUS_PARTIAL_CONTENT]="Partial Content";
		vec[STATUS_MULTIPLE_CHOICES]="Multiple Choices";
		vec[STATUS_MOVED_PERMANENTLY]="Moved Permanently";
		vec[STATUS_FOUND]="Found";
		vec[STATUS_SEE_OTHER]="See Other";
		vec[STATUS_NOT_MODIFIED]="Not Modified";
		vec[STATUS_USE_PROXY]="Use Proxy";
		vec[STATUS_TEMPORARY_REDIRECT]="Temporary Redirect";
		vec[STATUS_BAD_REQUEST]="Bad Request";
		vec[STATUS_UNAUTHORIZED]="Unauthorized";
		vec[STATUS_PAYMENT_REQUIRED]="Payment Required";
		vec[STATUS_FORBIDDEN]="Forbidden";
		vec[STATUS_NOT_FOUND]="Not Found";
		vec[STATUS_METHOD_NOT_ALLOWED]="Method Not Allowed";
		vec[STATUS_NOT_ACCEPTABLE]="Not Acceptable";
		vec[STATUS_PROXY_AUTHENTICATION_REQUIRED]="Proxy Authentication Required";
		vec[STATUS_REQUEST_TIME_OUT]="Request Time-out";
		vec[STATUS_CONFLICT]="Conflict";
		vec[STATUS_GONE]="Gone";
		vec[STATUS_LENGTH_REQUIRED]="Length Required";
		vec[STATUS_PRECONDITION_FAILED]="Precondition Failed";
		vec[STATUS_REQUEST_ENTITY_TOO_LARGE]="Request Entity Too Large";
		vec[STATUS_REQUEST_URI_TOO_LARGE]="Request-URI Too Large";
		vec[STATUS_UNSUPPORTED_MEDIA_TYPE]="Unsupported Media Type";
		vec[STATUS_REQUESTED_RANGE_NOT_SATISFIABLE]="Requested range not satisfiable";
		vec[STATUS_EXPECTATION_FAILED]="Expectation Failed";
		vec[STATUS_INTERNAL_SERVER_ERROR]="Internal Server Error";
		vec[STATUS_NOT_IMPLEMENTED]="Not Implemented";
		vec[STATUS_BAD_GATEWAY]="Bad Gateway";
		vec[STATUS_SERVICE_UNAVAILABLE]="Service Unavailable";
		vec[STATUS_GATEWAY_TIME_OUT]="Gateway Time-out";
		vec[STATUS_HTTP_VERSION_NOT_SUPPORTED]="HTTP Version not supported";	
		return vec;
	}

	static std::vector<std::string> statusStrings = status_init();
}
#endif