#include "init_ui.h"
#include "ui_init_ui.h"


init_ui::init_ui(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::init_ui)
{
    ui->setupUi(this);
    // installEventFilter
    ui->ori_1->installEventFilter(this);
    ui->ori_2->installEventFilter(this);
    ui->ori_3->installEventFilter(this);
    ui->ori_4->installEventFilter(this);

    // init
    lab = {ui->ori_1, ui->ori_2, ui->ori_3, ui->ori_4};
    pic_path = {":/images/images/Feiouna1.png", ":/images/images/mc.png", ":/images/images/touxiang.png", ":/images/images/xingkong.png"};
    pic_init();

    // connect the signals and slots;
    er = new puzzle();
    QObject::connect(this, SIGNAL(sendPath(string)), er, SLOT(receivePath(string)));
}

init_ui::~init_ui()
{
    delete ui;
}

bool init_ui::eventFilter(QObject *obj, QEvent *event)
{
    if (event->type() == QEvent::MouseButtonPress){
        for(int i = 0; i < 4; ++i){
            if(obj == lab[i]){
                emit sendPath(pic_path[i]);
                er->show();
                this->close();
                break;
            }
        }
    }
    return false;
}

void init_ui::pic_init(){
    for(int i = 0; i < 4; ++i){
        QPixmap cur_Pixmap(pic_path[i].c_str());
        cur_Pixmap = cur_Pixmap.scaled(200, 200);
        lab[i]->setPixmap(cur_Pixmap);
    }
}

//void init_ui::on_pushButton_clicked(){

//}
