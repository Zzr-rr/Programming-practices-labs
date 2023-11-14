#ifndef PUZZLE_H
#define PUZZLE_H
#include <QWidget>
#include <QMouseEvent>
#include <QKeyEvent>
#include <QDateTime>
#include <QTimer>
#include <vector>
#include <iostream>
#include <random>
#include <algorithm>
#include <QLabel>
#include <QMessageBox>
#include <string>
#include <QObject>
#include "init_ui.h"

class init_ui;
using namespace std;

namespace Ui {
    class puzzle;
}

class puzzle : public QWidget
{
    Q_OBJECT

public:
    explicit puzzle(QWidget *parent = nullptr);
    // variables
    vector<QLabel*> lab;
    vector<QPixmap> image;
    vector<int> shuffle_order;
    vector<int> ori_order;

    // rewrite the eventFilter;
    QObject *first_obj;
    QLabel* cur_label;
    bool exchange;

    // swap the img
    void swapImg(QObject* obj_1, QObject* obj_2);
    QPixmap originalPixmap;


    // if the game finished;
    bool finished();
    // connect the slots and signals
    ~puzzle();
protected:
    bool eventFilter(QObject *obj, QEvent *event);

private slots:
    void on_pushButton_1_clicked();
    void on_pushButton_2_clicked();
    void showImg();
    void receivePath(string s);

private:
    Ui::puzzle *ui;
};

#endif // PUZZLE_H

