#ifndef HIDROGINE_DRAW_HANDLER
#define HIDROGINE_DRAW_HANDLER
#include "Group.h"
#include "Material.h"
namespace hidrogine {
	class DrawHandler {
		public: virtual void beforeDraw(Group * group, Material * material)=0;
		public: virtual void afterDraw(Group * group, Material * material)=0;
	};
}
#endif