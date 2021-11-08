#include<stdlib.h>
#include<stdio.h>
#define MAX_SIZE 5
#include "Traveler.h"
#include "Environment.h"
#include "Position.h"
int priorityPath[8] = { 0 };
int targetsCollected[4] = { 0 };
int previousPathX[1000]; //Needs to be at least the number of steps set in MMTS.c
int previousPathY[1000];
int * ENEMY_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * X_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * Y_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int fuel=0;
int min_fuel=0;
int max_fuel=0; 
int _y = 0;
int _x = 0;
int active;
int validPosition_X[8] = { 0 };
int validPosition_Y[8] = { 0 };
int waiting = 0;
void setup(int x, int y, int startFuel, int manualEnemySet){
	fuel = Set_Fuel(startFuel);
	min_fuel = Set_MinFuel(0);
	max_fuel = Set_MaxFuel(100);
	_x = Set_PositionX(x);
	_y = Set_PositionY(y);
	enemy_Position(ENEMY_POSITION,manualEnemySet);
	x_Coordinate(X_POSITION);
	y_Coordinate(Y_POSITION);
	printf("x = %d, y = %d", x, y);
	printf("_x = %d, _y = %d", _x, _y);
}
int update(int caseId)
{	
	int proceed = 0;
	int fuelCheckPassed = Check_FuelConsistency(fuel, min_fuel, max_fuel);
	printf(" x = %d, y =%d and Fuel = %d\n", _x, _y, fuel);
	// gCS start
	if (fuelCheckPassed)
	{
		
		int enemyCheckPassed = CheckEnemy(ENEMY_POSITION, X_POSITION,
			Y_POSITION, _x, _y);
		if (!enemyCheckPassed)
		{
			// gCS END
			// gNP Start
			int canMove = computeValidMoves(ENEMY_POSITION, X_POSITION,
				Y_POSITION, _x, _y, validPosition_X, validPosition_Y, fuel, min_fuel, max_fuel);
			if (canMove)
			{
				checkMovePriority(caseId);
				// sP End
				
				//Start of checking for target collection
				if (_x == 2 && _y == 2){
					targetsCollected[0] = 1;
				}
				if (_x == -2 && _y == 2){
					targetsCollected[1] = 1;
				}
				if (_x == -2 && _y == -2){
					targetsCollected[2] = 1;
				}
				if (_x == 2 && _y == -2){
					targetsCollected[3] = 1;
				}
				// for(int i = 0; i < 4; i++){
				// 	printf("Target %i: %i, ", i, targetsCollected[i]);
				// }
				// printf("\n");
				
			}
			else
			{ 
				active = Set_State(0);
			}
		}
	}
	return 1;
}
int generateRandomNextMove(int validPosition_X[8], int  validPosition_Y[8])
{
	printf("_x = %d, _y = %d", _x, _y);	for(int i = 0; i < 8; i++){
		// printf("prioritypath[%i]: %i", i, priorityPath[i]);
		if(priorityPath[i] == 1 && validPosition_X[i] > -10000){
			priorityPath[i] = 0;
#include<stdlib.h>
#include<stdio.h>
#define MAX_SIZE 5
#include "Traveler.h"
#include "Environment.h"
#include "Position.h"
int priorityPath[8] = { 0 };
int targetsCollected[4] = { 0 };
int previousPathX[1000]; //Needs to be at least the number of steps set in MMTS.c
int previousPathY[1000];
int * ENEMY_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * X_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * Y_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int fuel=0;
int min_fuel=0;
int max_fuel=0; 
int _y = 0;
int _x = 0;
int active;
int validPosition_X[8] = { 0 };
int validPosition_Y[8] = { 0 };
int waiting = 0;
void setup(int x, int y, int startFuel, int manualEnemySet){
	fuel = Set_Fuel(startFuel);
	min_fuel = Set_MinFuel(0);
	max_fuel = Set_MaxFuel(100);
	_x = Set_PositionX(x);
	_y = Set_PositionY(y);
	enemy_Position(ENEMY_POSITION,manualEnemySet);
	x_Coordinate(X_POSITION);
	y_Coordinate(Y_POSITION);
	printf("x = %d, y = %d", x, y);
	printf("_x = %d, _y = %d", _x, _y);
}
int update(int caseId)
{	
	int proceed = 0;
	int fuelCheckPassed = Check_FuelConsistency(fuel, min_fuel, max_fuel);
	printf(" x = %d, y =%d and Fuel = %d\n", _x, _y, fuel);
	// gCS start
	if (fuelCheckPassed)
	{
		
		int enemyCheckPassed = CheckEnemy(ENEMY_POSITION, X_POSITION,
			Y_POSITION, _x, _y);
		if (!enemyCheckPassed)
		{
			// gCS END
			// gNP Start
			int canMove = computeValidMoves(ENEMY_POSITION, X_POSITION,
				Y_POSITION, _x, _y, validPosition_X, validPosition_Y, fuel, min_fuel, max_fuel);
			if (canMove)
			{
				checkMovePriority(caseId);
				// sP End
				
				//Start of checking for target collection
				if (_x == 2 && _y == 2){
					targetsCollected[0] = 1;
				}
				if (_x == -2 && _y == 2){
					targetsCollected[1] = 1;
				}
				if (_x == -2 && _y == -2){
					targetsCollected[2] = 1;
				}
				if (_x == 2 && _y == -2){
					targetsCollected[3] = 1;
				}
				// for(int i = 0; i < 4; i++){
				// 	printf("Target %i: %i, ", i, targetsCollected[i]);
				// }
				// printf("\n");
				
			}
			else
			{ 
				active = Set_State(0);
			}
		}
	}
	return 1;
}
int generateRandomNextMove(int validPosition_X[8], int  validPosition_Y[8])
{
	if(waiting){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				for(int k = 0; k < 8; k++){
					if(validPosition_X[i]==previousPathX[k] && validPosition_Y[j] == previousPathY[k]){
						priorityPath[i]=1;
					}
				}
			}
		}
	}
	for(int i = 0; i < 8; i++){
		// printf("prioritypath[%i]: %i", i, priorityPath[i]);
		if(priorityPath[i] == 1 && validPosition_X[i] > -10000){
			priorityPath[i] = 0;
			return i;
		}
	}
	int randomIndex = -1;
	while (randomIndex == -1)
	{
		
		int index = rand() % 8;
		if (validPosition_X[index] == -100000 && validPosition_Y[index] == -100000)
		{
			randomIndex = -1;
		}
		else
		{
			randomIndex = index;
		}
	}
	return randomIndex;
}
int computeValidMoves(int ENEMY_POSITION[MAX_SIZE][MAX_SIZE], int X_POSITION[MAX_SIZE][MAX_SIZE]
	, int Y_POSITION[MAX_SIZE][MAX_SIZE],int _x, int _y, int  validPosition_X[8], int validPosition_Y[8],
	int _fuel, int _min_fuel, int _max_fuel)
{
	int validPosition = 0;
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION, 
		X_POSITION,Y_POSITION, _x, _y + 1))
	{
		validPosition_X[0] = _x + 0; validPosition_Y[0] = _y + 1;
	}
	else
	{
		validPosition_X[0] = -100000; validPosition_Y[0] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x , _y - 1))
	{
		validPosition_X[1] = _x + 0; validPosition_Y[1] = _y - 1;
	}
	else
	{
		validPosition_X[1] = -100000; validPosition_Y[1] = -100000;
	}
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y))
	{
		validPosition_X[2] = _x + 1; validPosition_Y[2] = _y + 0;
	}
	else
	{
		validPosition_X[2] = -100000; validPosition_Y[2] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y))
	{
		validPosition_X[3] = _x - 1; validPosition_Y[3] = _y + 0;
	}
	else
	{
		validPosition_X[3] = -100000; validPosition_Y[3] = -100000;
	}
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y + 1))
	{
		validPosition_X[4] = _x + 1; validPosition_Y[4] = _y + 1;
	}
	else
	{
		validPosition_X[4] = -100000; validPosition_Y[4] = -100000;
	}
	if (Check_FuelConsistency(_fuel , _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y - 1))
	{
		validPosition_X[5] = _x + 1; validPosition_Y[5] = _y - 1;
	}
	else
	{
		validPosition_X[5] = -100000; validPosition_Y[5] = -100000;
	}
	if (Check_FuelConsistency(_fuel, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y + 1))
	{
		validPosition_X[6] = _x - 1; validPosition_Y[6] = _y + 1;
	}
	else
	{
		validPosition_X[6] = -100000; validPosition_Y[6] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y - 1))
	{
		validPosition_X[7] = _x - 1; validPosition_Y[7] = _y - 1;
	}
	else
	{
		targetsCollected[0] = 1;validPosition_X[7] = -100000; validPosition_Y[7] = -100000;
	}
	for (int i = 0; i < 8; i++)
	{
#include<stdlib.h>
#include<stdio.h>
#define MAX_SIZE 5
#include "Traveler.h"
#include "Environment.h"
#include "Position.h"
int priorityPath[8] = { 0 };
int targetsCollected[4] = { 0 };
int previousPathX[1000]; //Needs to be at least the number of steps set in MMTS.c
int previousPathY[1000];
int * ENEMY_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * X_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int * Y_POSITION[MAX_SIZE][MAX_SIZE] = {0};
int fuel=0;
int min_fuel=0;
int max_fuel=0; 
int _y = 0;
int _x = 0;
int active;
int validPosition_X[8] = { 0 };
int validPosition_Y[8] = { 0 };
int waiting = 0;
void setup(int x, int y, int startFuel, int manualEnemySet){
	fuel = Set_Fuel(startFuel);
	min_fuel = Set_MinFuel(0);
	max_fuel = Set_MaxFuel(100);
	_x = Set_PositionX(x);
	_y = Set_PositionY(y);
	enemy_Position(ENEMY_POSITION,manualEnemySet);
	x_Coordinate(X_POSITION);
	y_Coordinate(Y_POSITION);
	printf("x = %d, y = %d", x, y);
	printf("_x = %d, _y = %d", _x, _y);
}
int update(int caseId)
{	
	int proceed = 0;
	int fuelCheckPassed = Check_FuelConsistency(fuel, min_fuel, max_fuel);
	printf(" x = %d, y =%d and Fuel = %d\n", _x, _y, fuel);
	// gCS start
	if (fuelCheckPassed)
	{
		
		int enemyCheckPassed = CheckEnemy(ENEMY_POSITION, X_POSITION,
			Y_POSITION, _x, _y);
		if (!enemyCheckPassed)
		{
			// gCS END
			// gNP Start
			int canMove = computeValidMoves(ENEMY_POSITION, X_POSITION,
				Y_POSITION, _x, _y, validPosition_X, validPosition_Y, fuel, min_fuel, max_fuel);
			if (canMove)
			{
				checkMovePriority(caseId);
				// sP End
				
				//Start of checking for target collection
				if (_x == 2 && _y == 2){
					targetsCollected[0] = 1;
				}
				if (_x == -2 && _y == 2){
					targetsCollected[1] = 1;
				}
				if (_x == -2 && _y == -2){
					targetsCollected[2] = 1;
				}
				if (_x == 2 && _y == -2){
					targetsCollected[3] = 1;
				}
				// for(int i = 0; i < 4; i++){
				// 	printf("Target %i: %i, ", i, targetsCollected[i]);
				// }
				// printf("\n");
				
			}
			else
			{ 
				active = Set_State(0);
			}
		}
	}
	return 1;
}
int generateRandomNextMove(int validPosition_X[8], int  validPosition_Y[8])
{
	if(waiting){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				for(int k = 0; k < 8; k++){
					if(validPosition_X[i]==previousPathX[k] && validPosition_Y[j] == previousPathY[k]){
						priorityPath[i]=1;
					}
				}
			}
		}
	}
	for(int i = 0; i < 8; i++){
		// printf("prioritypath[%i]: %i", i, priorityPath[i]);
		if(priorityPath[i] == 1 && validPosition_X[i] > -10000){
			priorityPath[i] = 0;
			return i;
		}
	}
	int randomIndex = -1;
	while (randomIndex == -1)
	{
		
		int index = rand() % 8;
		if (validPosition_X[index] == -100000 && validPosition_Y[index] == -100000)
		{
			randomIndex = -1;
		}
		else
		{
			randomIndex = index;
		}
	}
	return randomIndex;
}
int computeValidMoves(int ENEMY_POSITION[MAX_SIZE][MAX_SIZE], int X_POSITION[MAX_SIZE][MAX_SIZE]
	, int Y_POSITION[MAX_SIZE][MAX_SIZE],int _x, int _y, int  validPosition_X[8], int validPosition_Y[8],
	int _fuel, int _min_fuel, int _max_fuel)
{
	int validPosition = 0;
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION, 
		X_POSITION,Y_POSITION, _x, _y + 1))
	{
		validPosition_X[0] = _x + 0; validPosition_Y[0] = _y + 1;
	}
	else
	{
		validPosition_X[0] = -100000; validPosition_Y[0] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x , _y - 1))
	{
		validPosition_X[1] = _x + 0; validPosition_Y[1] = _y - 1;
	}
	else
	{
		validPosition_X[1] = -100000; validPosition_Y[1] = -100000;
	}
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y))
	{
		validPosition_X[2] = _x + 1; validPosition_Y[2] = _y + 0;
	}
	else
	{
		validPosition_X[2] = -100000; validPosition_Y[2] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y))
	{
		validPosition_X[3] = _x - 1; validPosition_Y[3] = _y + 0;
	}
	else
	{
		validPosition_X[3] = -100000; validPosition_Y[3] = -100000;
	}
	if (Check_FuelConsistency(_fuel + 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y + 1))
	{
		validPosition_X[4] = _x + 1; validPosition_Y[4] = _y + 1;
	}
	else
	{
		validPosition_X[4] = -100000; validPosition_Y[4] = -100000;
	}
	if (Check_FuelConsistency(_fuel , _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x + 1, _y - 1))
	{
		validPosition_X[5] = _x + 1; validPosition_Y[5] = _y - 1;
	}
	else
	{
		validPosition_X[5] = -100000; validPosition_Y[5] = -100000;
	}
	if (Check_FuelConsistency(_fuel, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y + 1))
	{
		validPosition_X[6] = _x - 1; validPosition_Y[6] = _y + 1;
	}
	else
	{
		validPosition_X[6] = -100000; validPosition_Y[6] = -100000;
	}
	if (Check_FuelConsistency(_fuel - 1, _min_fuel, _max_fuel) && !CheckEnemy(ENEMY_POSITION,
		X_POSITION, Y_POSITION, _x - 1, _y - 1))
	{
		validPosition_X[7] = _x - 1; validPosition_Y[7] = _y - 1;
	}
	else
	{
		validPosition_X[7] = -100000; validPosition_Y[7] = -100000;
	}
	for (int i = 0; i < 8; i++)
	{
		if (!(validPosition_X[i] == -100000 && validPosition_Y[i] == -100000))
			validPosition++;
	}
	return validPosition;
}
int CheckEnemy(int ENEMY_POSITION[MAX_SIZE][MAX_SIZE], int X_POSITION[MAX_SIZE][MAX_SIZE]
	, int Y_POSITION[MAX_SIZE][MAX_SIZE], int _x, int _y)
{
	int enemyPos = 0;
	for (int x = 0; x < MAX_SIZE; x++)
	{
		for (int y = 0; y < MAX_SIZE; y++)
		{
			if (X_POSITION[x][y] == _x && Y_POSITION[x][y] == _y)
			{
				if (ENEMY_POSITION[x][y] == 1)
				{
					//waiting = 1;
					enemyPos = 1;
				}
				else
				{
					enemyPos = 0;
				}
				break;
			}
		}
	}
	return enemyPos;
}
int getChangeFuel(int _x, int _y, int _newPosX, int _newPosY)
{
	int changeX = _newPosX - _x;
	int changeY = _newPosY - _y;
	if (changeX == 1)
	{
		// move to south-east and no change in fuel 
		if (changeY == -1)
		{
			return 0;
		}
		// move to north-east or east and increase fuel 
		else
		{
			return 1;
		}
	}
	else if (changeX == 0)
	{
		// move to south and decrease fuel 
		if (changeY == -1)
		{
			return -1;
		}
		// move to north and increase fuel 
		else
		{
			return 1;
		}
	}
	else if (changeX == -1)
	{
		// move to north-west and no change in fuel 
		if (changeY == 1)
		{
			return 0;
		}
		// move to south-west or south and decrease fuel 
		else
		{
			return -1;
		}
	}
	else
	{
		// For any other move, no change in fuel.
		return 0;
	}
}
int checkMovePriority(int moveNumber){
	switch(moveNumber){
		case 0:
			priorityPath[4] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[4] = 0;
			break;
		case 1:
			priorityPath[4] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[4] = 0;
			break;
		case 2:
			priorityPath[3] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[3] = 0;
			break;
		case 3:
			priorityPath[3] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[3] = 0;
			break;
		case 4:
			priorityPath[3] = 1;
			moveTraveler(priorityPath, moveNumber);
			validPosition_X[3] = -100000;
			break;
		case 5:
			priorityPath[3] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[3] = 0;
			break;
		case 6:
			priorityPath[1] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[1] = 0;
			break;
		case 7:
			priorityPath[1] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[1] = 0;
			break;
		case 8:
			priorityPath[1] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[1] = 0;
			break;
		case 9:
			priorityPath[1] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[1] = 0;
			break;
		case 10:
			priorityPath[2] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[2] = 0;
			break;
		case 11:
			priorityPath[2] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[2] = 0;
			break;
		case 12:
			priorityPath[2] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[2] = 0;
			break;
		case 13:
			priorityPath[2] = 1;
			moveTraveler(priorityPath, moveNumber);
			priorityPath[2] = 0;
			break;
		default:
			moveTraveler(priorityPath, moveNumber);
			break;
	}
	return 1;	
}
int * getTargetsCollected(){
	return targetsCollected;
}
int moveTraveler(int * priorityPath, int moveNumber){
	int newPos = generateRandomNextMove(validPosition_X, validPosition_Y);
	int previouslyTread = 0;
	int changeFuel = getChangeFuel(_x, _y, validPosition_X[newPos], validPosition_Y[newPos]);
	_x = validPosition_X[newPos];
	_y = validPosition_Y[newPos];
	if(waiting){
		for(int i = 0; i < moveNumber; i++){
			if(previousPathX[i] == _x && previousPathY[i] == _y){
				previouslyTread = 1;
				break;
			}		
		}
	}
	if(!previouslyTread){
		previousPathX[moveNumber] = _x;
		previousPathY[moveNumber] = _y;
	}
	fuel = fuel + changeFuel;
	moveNumber = moveNumber - 1;
	return 1;
}
