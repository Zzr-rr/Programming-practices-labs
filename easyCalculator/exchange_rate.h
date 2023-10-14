#ifndef EXCHANGE_RATE_H
#define EXCHANGE_RATE_H

#include <QWidget>
#include <iostream>
#include <unordered_map>
#include "caculator.h"
using namespace std;

namespace Ui {
class exchange_rate;
}

class exchange_rate : public QWidget
{
    Q_OBJECT


public:
    explicit exchange_rate(QWidget *parent = nullptr);
    float exp_cal(string origin_data, QString origin_unit,QString transmit_unit);
    ~exchange_rate();

private slots:
    void on_origin_edit_textEdited(const QString &arg1);

    void on_transmit_edit_textEdited(const QString &arg1);

    void on_pushButton_clicked();

    void on_transmit_clicked();

private:
    Ui::exchange_rate *ui;
};

#endif // EXCHANGE_RATE_H
