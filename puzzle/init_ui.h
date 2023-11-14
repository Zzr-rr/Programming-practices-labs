#ifndef INIT_UI_H
#define INIT_UI_H
#include <QMainWindow>
#include <iostream>
#include <QWidget>
#include <QMouseEvent>
#include <QKeyEvent>
#include <vector>
#include <QLabel>
#include <QMessageBox>
#include <QObject>
#include "puzzle.h"

class puzzle;
using namespace std;

QT_BEGIN_NAMESPACE
namespace Ui { class init_ui; }
QT_END_NAMESPACE

class init_ui : public QMainWindow
{
    Q_OBJECT

public:
    init_ui(QWidget *parent = nullptr);

    // init
    vector<string> pic_path;
    vector<QLabel*> lab;
    QLabel* cur_obj;
    void pic_init();

    //
    ~init_ui();

protected:
    bool eventFilter(QObject *obj, QEvent *event);

signals:
    void sendPath(string s);


private slots:
    // void on_pushButton_clicked();

private:
    Ui::init_ui *ui;
    puzzle *er;
};
#endif //INIT_UI_H
