#include "express_caculate.h"
#include <iostream>
#include <stack>
using namespace std;
express_caculate::express_caculate(){

}
bool express_caculate::isNum(string x){
    if(x == "+" || x == "-" || x == "*" || x == "/" || x == "(" || x == ")") return false;
    return true;
}

bool express_caculate::isOpe(string x){
    if(x == "+" || x == "-" || x == "*" || x == "/") return true;
    return false;
}

float express_caculate::binaryOperation(float num_top1, float num_top2, string ope){
    float result = 0;

    if(ope == "+"){
        result = num_top2 + num_top1;
    }
    else if(ope == "-"){
        result = num_top2 - num_top1;
    }
    else if(ope == "*"){
        result = num_top2 * num_top1;
    }
    else if(ope == "/"){
        result = num_top2 / num_top1;
    }
    return result;
}
// exp:{"1", "+", "2", "+", "3", "*", "(", "1", "+", "7", "*", "12", ")"};
bool express_caculate::isValid(vector<string> exp){
    int left_bracket = 0, right_bracket = 0, num_count = 0;
    int n = exp.size();

    for(int i = 0; i < n; ++i){

        if(isNum(exp[i])){
            if(exp[i].size() > 16) return false;
            num_count ++;
            if(i == n - 1){
                if(left_bracket == right_bracket) return true;
                else return false;
            }
            else if(!isOpe(exp[i + 1]) && exp[i + 1] != ")"){
                cout << exp[i+1] << endl;
                return false;
            }
        }
        else if(isOpe(exp[i])){
            if(i == n - 1) return false;
            else if(exp[i] == "/" && exp[i + 1] == "0") return false;
            else if(!isNum(exp[i + 1]) && exp[i + 1] != "(") return false;
        }
        else{
            if(exp[i] == "("){
                if(i == n - 1) return false;
                left_bracket ++;
            }
            if(exp[i] == ")"){
                if(i == n - 1 && right_bracket + 1 == left_bracket && num_count != 0) return true;
                right_bracket ++;
            }
            if(right_bracket > left_bracket) return false;
        }
    }
    if(right_bracket != left_bracket) return false;
    if(exp[n - 1] != ")" || !isNum(exp[n - 1])) return false;
    return true;
}

float express_caculate::expCalculate(vector<string> exp){
    stack<float> numStack;    // store the nums
    stack<string> opStack;  // store the operators
    unordered_map<string, int> priority;
    priority["+"] = 1; priority["-"] = -1; priority["*"] = 2; priority["/"] = 2; priority["("] = 0;
    float num_top1, num_top2;
    string ope_top;
    if(isOpe(exp[0])) numStack.push(0);
    for(string &element:exp){
        cout << "element=" << element << endl;
        if(isNum(element)) numStack.push(stof(element));    // num
        else if(isOpe(element)){                            // ope
            if(opStack.empty()){
                opStack.push(element);
                continue;
            }
            ope_top = opStack.top();
            if(!opStack.empty() && priority[ope_top] >= priority[element]){
                num_top1 = numStack.top();
                opStack.pop();
                numStack.pop();
                num_top2 = numStack.top();
                numStack.pop();
                float result = binaryOperation(num_top1, num_top2, ope_top);
                numStack.push(result);
            }
            opStack.push(element);
        }
        else{
            if(element == "(") opStack.push(element);
            else{
                while(opStack.top() != "("){
                    ope_top = opStack.top();
                    opStack.pop();
                    num_top1 = numStack.top();
                    numStack.pop();
                    num_top2 = numStack.top();
                    numStack.pop();
                    float result = binaryOperation(num_top1, num_top2, ope_top);
                    numStack.push(result);
                }
                opStack.pop();
            }
        }
    }
    while(!opStack.empty()){
        ope_top = opStack.top();
        opStack.pop();
        num_top1 = numStack.top();
        numStack.pop();
        num_top2 = numStack.top();
        numStack.pop();
        float result = binaryOperation(num_top1, num_top2, ope_top);
        numStack.push(result);
    }
    return numStack.top();
}
