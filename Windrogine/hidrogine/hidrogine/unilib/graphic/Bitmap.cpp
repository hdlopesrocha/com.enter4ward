#include "Graphic.hpp"

namespace unilib {


	int COLOR::clamp(int val, int min, int max){
		return (val < min) ? min : (val > max) ? max : val;
	}

	COLOR::COLOR(int r,int g, int b){
		R = clamp(r,0x00,0xFF);
		G = clamp(g,0x00,0xFF);
		B = clamp(b,0x00,0xFF);
	}


	BITMAP::BITMAP(int width, int height){
		_bytesPerPixel = 3;
		_height = height;
		_width = width;

		_plot_l = _plot_c = -1;

		// AUX 
		_padding = (_width*_bytesPerPixel)%4;
		_bytesPerLine = (_bytesPerPixel*_width+_padding);
		_offset = 54;
		_size = 54+_bytesPerLine*_height;
		int raw = width*height;

		_data = new char[_size];

		// header
		_data[0]=0x42;	// ID FIELD
		_data[1]=0x4D;
		
		_data[2]=(_size & 0xFF)>>0; 	// SIZE OF FILE (bytes)
		_data[3]=(_size & 0xFF00)>>8;	
		_data[4]=(_size & 0xFF0000)>>16;	
		_data[5]=(_size & 0xFF000000)>>24;	

		_data[10]=(_offset & 0xFF)>>0; 	// OFFSET (first pixel)
		_data[11]=(_offset & 0xFF00)>>8; 	
		_data[12]=(_offset & 0xFF0000)>>16; 
		_data[13]=(_offset & 0xFF000000)>>24; 

		_data[14]=0x28;	//  SIZE OF DIB HEADER
		_data[15]=0x00;
		_data[16]=0x00;
		_data[17]=0x00;


		_data[18]=(width & 0xFF)>>0;	// BMP WIDTH
		_data[19]=(width & 0xFF00)>>8;
		_data[20]=(width & 0xFF0000)>>16;
		_data[21]=(width & 0xFF000000)>>24;


		_data[22]=(height & 0xFF)>>0;	// BMP HEIGHT
		_data[23]=(height & 0xFF00)>>8;
		_data[24]=(height & 0xFF0000)>>16;
		_data[25]=(height & 0xFF000000)>>24;


		_data[26]=0x01;	// NUMBER OF PLANES
		_data[28]=0x18;	// BITS PER PIXEL

		_data[34]=(raw & 0xFF)>>0; // SIZE OF RAW DATA
		_data[35]=(raw & 0xFF00)>>8;
		_data[36]=(raw & 0xFF0000)>>16;
		_data[37]=(raw & 0xFF000000)>>24;


		_data[38]=0x13;	// HORIZONTAL RESOLUTION
		_data[39]=0x0B;
		_data[42]=0x13;	// VERTICAL RESOLUTION
		_data[43]=0x0B;

	}

	BITMAP::BITMAP(string path){
		ifstream file (path.c_str());
		int begin,end;

		if(file.is_open())
		{
			file.seekg(0, ios::end);
			end = file.tellg();

		    file.seekg(0, ios::beg);
			begin = file.tellg();

		    _size = end - begin;
			_data = new char[_size];

		    
		    if(!file.read(_data, _size))
		        cout << "fail to read" << endl;
		    file.close();

		    cout << "size: " << _size << endl;
		}

	}




	void BITMAP::Clear(COLOR pixel){
		int i = _offset;
		while(i<_size){
			_data[i++]=pixel.B;
			_data[i++]=pixel.G;
			_data[i++]=pixel.R;
			i+=_padding;
		}		
	}

	void BITMAP::PixelPlot(int line, int column, COLOR pixel){
	
		PutPixel(line, column, pixel);

		// plot between spaces
		if(_plot_l>=0){
			while(_plot_l!=line){
				PutPixel(_plot_l, _plot_c, pixel);
				if(_plot_l < line)
					++_plot_l;
				else
					--_plot_l;

			}
		}

		_plot_l = line;
		_plot_c = column;
	}

	void BITMAP::PlotReset(){
		_plot_c = _plot_l = -1;
	}



	void BITMAP::PutPixel(int line, int column, COLOR pixel){

		if(line>=0 && line<_height && column>=0 && column<_width){
			int i = _offset+line*_bytesPerLine + column*_bytesPerPixel;
			_data[i++]=pixel.B;
			_data[i++]=pixel.G;
			_data[i++]=pixel.R;
		}
	}

	COLOR BITMAP::GetPixel(int line, int column){
		if(line>=0 && line<_height && column>=0 && column<_width){
			int i = _offset+line*_bytesPerLine + column*_bytesPerPixel;
			return COLOR(_data[i+2],_data[i+1],_data[i+0]);
		}
		return COLOR(0,0,0);
	}


	void BITMAP::Save(string path){
		ofstream outfile (path.c_str());
		outfile.write(_data,_size);
		outfile.close();
	}

	int BITMAP::GetHeight(){
		return _height;
	}

	int BITMAP::GetWidth(){
		return _width;
	}

}