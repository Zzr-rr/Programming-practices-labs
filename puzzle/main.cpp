#include "init_ui.h"
#include "puzzle.h"
#include <QObject>

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    init_ui w;
    w.show();
    return a.exec();
}
