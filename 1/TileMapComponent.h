
//
#pragma once

#include"SpriteComponent.h"
#include"SDL/SDL.h"
#include"SDL/SDL_image.h"
#include"CSVFile.h"
#include"Math.h"
typedef vector<vector<int> > MatrixInt;
class TileMapComponent : public SpriteComponent {
public:
	TileMapComponent(class Actor* owner,int drawOrder=100);
	virtual void Draw(SDL_Renderer* renderer);
private:
	MatrixInt mInt;
	MatrixString mString;
	void TransToInt(MatrixInt& _out , MatrixString& _in);
	void GetMatrixString();
};
