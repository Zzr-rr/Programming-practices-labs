#ifndef EXPRESS_CACULATE_H
#define EXPRESS_CACULATE_H
#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
using namespace std;



class express_caculate
{
public:
    express_caculate();
    bool isNum(string x);
    bool isOpe(string x);
    float binaryOperation(float num_top1, float num_top2, string ope);
    bool isValid(vector<string> exp);
    float expCalculate(vector<string> exp);
};

#endif // EXPRESS_CACULATE_H
