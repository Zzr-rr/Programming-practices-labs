#include "caculator.h"
#include "exchange_rate.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    caculator w;
    w.show();
    return a.exec();
}
