#include "caculator.h"
#include "ui_caculator.h"
#include "express_caculate.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;


caculator::caculator(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::caculator)
{
    ui->setupUi(this);
}

caculator::~caculator()
{
    delete ui;
}


void caculator::on_empty_2_clicked()
{
    ui->express->clear();
}

void caculator::on_number0_2_clicked()
{
    ui->express->insertPlainText("0");
}

void caculator::on_number1_2_clicked()
{
    ui->express->insertPlainText("1");
}


void caculator::on_number2_2_clicked()
{
    ui->express->insertPlainText("2");
}



void caculator::on_number3_2_clicked()
{
    ui->express->insertPlainText("3");
}



void caculator::on_number4_2_clicked()
{
    ui->express->insertPlainText("4");
}



void caculator::on_number5_2_clicked()
{
    ui->express->insertPlainText("5");
}


void caculator::on_number6_2_clicked()
{
    ui->express->insertPlainText("6");
}



void caculator::on_number7_2_clicked()
{
    ui->express->insertPlainText("7");
}


void caculator::on_number8_2_clicked()
{
    ui->express->insertPlainText("8");
}


void caculator::on_number9_2_clicked()
{
    ui->express->insertPlainText("9");
}



void caculator::on_divise_clicked()
{
    ui->express->insertPlainText("/");
}



void caculator::on_multi_clicked()
{
    ui->express->insertPlainText("*");
}



void caculator::on_minus_clicked()
{
    ui->express->insertPlainText("-");
}


void caculator::on_plus_clicked()
{
    ui->express->insertPlainText("+");
}


void caculator::on_equal_2_clicked()
{
    QString qstr = ui->express->toPlainText();
    string s = qstr.toStdString();
    vector<string> exp;

    // handle the data.
    for(int i = 0; i < s.size();){
        string x = "";
        if(((int) s[i] >= 48 && (int)s[i] <= 57) || s[i] == '.'){
            if((i == 1 && s[i - 1] == '-') || (i > 2 && s[i-2] == '(' && s[i-1] == '-')){
                exp.pop_back();
                x += "-";
            }
            while(((int) s[i] >= 48 && (int)s[i] <= 57 ) || s[i] == '.'){
                x += s[i];
                i ++;
            }
            if(x == ".") continue;
        }
        else{
            x += s[i];
            ++i;
        }
        exp.push_back(x);
    }
    for(int i = 0; i < s.size(); ++i) cout << s[i] << " ";
    cout << endl;
    express_caculate cur_cal;
    if(!cur_cal.isValid(exp)){
        ui->express->setPlainText("Error Expression!");
    }
    else{
        float res = cur_cal.expCalculate(exp);
        // set the content
        QString m = QString::number(res);
        ui->express->setPlainText(m);
    }

    // move the cursor to the end;
    QTextCursor cursor(ui->express->document());
    cursor.movePosition(QTextCursor::End);
    ui->express->setTextCursor(cursor);
}


void caculator::on_left_bracket_clicked()
{
    ui->express->insertPlainText("(");
}


void caculator::on_right_bracket_clicked()
{
    ui->express->insertPlainText(")");
}



void caculator::on_Backspace_clicked()
{
    QTextCursor textCursor = ui->express->textCursor();
    textCursor.movePosition(QTextCursor:: End);
    QString qstr = ui->express->toPlainText();
    qstr.chop(1);
    ui->express->setPlainText(qstr);
    ui->express->setTextCursor(textCursor);
}




void caculator::on_dot_2_clicked()
{
    ui->express->insertPlainText(".");
}


void caculator::on_pushButton_3_clicked()
{
    exchange_rate *er = new exchange_rate;
    er->show();
    this->close();
}

