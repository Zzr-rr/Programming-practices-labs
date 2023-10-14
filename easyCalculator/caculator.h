#ifndef CACULATOR_H
#define CACULATOR_H

#include <QMainWindow>
#include <iostream>
#include <string>
#include <vector>
#include "exchange_rate.h"


QT_BEGIN_NAMESPACE
namespace Ui { class caculator; }
QT_END_NAMESPACE

class caculator : public QMainWindow
{
    Q_OBJECT


public:
    caculator(QWidget *parent = nullptr);
    ~caculator();

private slots:
    void on_empty_2_clicked();

    void on_number0_2_clicked();

    void on_number1_2_clicked();

    void on_number2_2_clicked();

    void on_number3_2_clicked();

    void on_number4_2_clicked();

    void on_number5_2_clicked();

    void on_number6_2_clicked();

    void on_number7_2_clicked();

    void on_number8_2_clicked();

    void on_number9_2_clicked();

    void on_divise_clicked();

    void on_multi_clicked();

    void on_minus_clicked();

    void on_plus_clicked();

    void on_equal_2_clicked();


    void on_left_bracket_clicked();

    void on_right_bracket_clicked();

    void on_Backspace_clicked();



    void on_dot_2_clicked();

    void on_pushButton_3_clicked();

private:
    Ui::caculator *ui;
};
#endif // CACULATOR_H
