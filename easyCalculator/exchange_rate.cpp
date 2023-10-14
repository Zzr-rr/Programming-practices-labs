#include "exchange_rate.h"
#include "ui_exchange_rate.h"

exchange_rate::exchange_rate(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::exchange_rate)
{
    ui->setupUi(this);
}

exchange_rate::~exchange_rate()
{
    delete ui;
}

float exchange_rate::exp_cal(string origin_data, QString origin_unit,QString transmit_unit){
    if(origin_data.size() == 0) return 0;
    unordered_map<QString, float> mp;
    mp["人民币(CNY)"] = 10;
    mp["(原神)原石"] = 1;
    mp["(CFM)点卷"] = 1;
    mp["(LOL)点卷"] = 1;
    mp["(王者荣耀)点卷"] = 1.667;
    mp["美丽币(USD)"] = 73.073;
    mp["坤"] = 25;
    float data = stof(origin_data);
    float x1 = mp[origin_unit];
    float x2 = mp[transmit_unit];
    float transmit_data = x1 * data / x2;
    cout << "transmit_data=" << transmit_data << endl;
    return transmit_data;
}
void exchange_rate::on_origin_edit_textEdited(const QString &arg1)
{
    string origin_data = arg1.toStdString();
    QString origin_unit = ui->origin_unit->currentText();
    QString transmit_unit = ui->transmit_unit->currentText();
    float result = exp_cal(origin_data, origin_unit, transmit_unit);
    QString m = QString::number(result);
    ui->transmit_edit->setText(m);
}


void exchange_rate::on_transmit_edit_textEdited(const QString &arg1)
{
    string transmit_data = arg1.toStdString();
    QString origin_unit = ui->origin_unit->currentText();
    QString transmit_unit = ui->transmit_unit->currentText();
    float result = exp_cal(transmit_data, transmit_unit, origin_unit);
    QString m = QString::number(result);
    ui->origin_edit->setText(m);
}


void exchange_rate::on_pushButton_clicked()
{

    on_origin_edit_textEdited(ui->origin_edit->text());
}


void exchange_rate::on_transmit_clicked()
{
    caculator *er = new caculator;
    er->show();
    this->close();
}

