#include "puzzle.h"
#include "ui_puzzle.h"


puzzle::puzzle(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::puzzle)
{
    ui->setupUi(this);

    // EventFilter;
    exchange = false;

    // installEventFilter
    ui->label_1->installEventFilter(this);
    ui->label_2->installEventFilter(this);
    ui->label_3->installEventFilter(this);
    ui->label_4->installEventFilter(this);
    ui->label_5->installEventFilter(this);
    ui->label_6->installEventFilter(this);
    ui->label_7->installEventFilter(this);
    ui->label_8->installEventFilter(this);
    ui->label_9->installEventFilter(this);

    // order
    shuffle_order = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    ori_order = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    // init the laboratory;
    lab = {ui->label_1, ui->label_2, ui->label_3, ui->label_4 ,ui->label_5,ui->label_6, ui->label_7, ui->label_8, ui->label_9};
}

puzzle::~puzzle()
{
    delete ui;
}

void puzzle::on_pushButton_1_clicked()
{
    // game start->lcd;
    // todo time->count


    // load the pic;
    // {1, 3, 5 ,6 ,7 ,8,...}
    // shuffle the pic;
    unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
    default_random_engine engine(seed);
    shuffle(shuffle_order.begin(), shuffle_order.end(), engine);

    // scale the pic;
    QPixmap scaledPixmap = originalPixmap.scaled(600, 600);


    // store the pic;
    for(int i = 0; i < 3; ++i){
        for(int j = 0; j < 3; ++j){
            QPixmap img = scaledPixmap.copy(j * 200, i * 200, 200, 200);
            image.push_back(img);
        }
    }
    showImg();
}



// eventFlilter
bool puzzle::eventFilter(QObject *obj, QEvent *event)
{
    if (event->type() == QEvent::MouseButtonPress){
        if(exchange){
            // swap;
            cur_label = qobject_cast<QLabel*>(first_obj);
            cur_label->setStyleSheet("");
            swapImg(first_obj, obj);
            exchange = false;
        }
        else{
            // selected;
            cur_label = qobject_cast<QLabel*>(obj);
            first_obj = obj;
            cur_label->setStyleSheet("QLabel { border: 2px solid black; padding: 5px; }");
            exchange = true;
        }
    }
    return false;
}


// show the img
void puzzle::showImg()
{
    for(int i = 0; i < 9; ++i){
        lab[i]->setPixmap(image[shuffle_order[i]]);
    }
}

// swap the img
void puzzle::swapImg(QObject* obj_1, QObject* obj_2){
    int index_obj_1;
    int index_obj_2;
    for(int i = 0; i < 9; ++i){
        if(obj_1 == lab[i]) index_obj_1 = i;
        if(obj_2 == lab[i]) index_obj_2 = i;
    }
    swap(shuffle_order[index_obj_1], shuffle_order[index_obj_2]);
    showImg();
    finished();
}


// game finished
bool puzzle::finished(){
    for(int i = 0; i < 9; ++i){
        if(shuffle_order[i] != i) return false;
    }
    QMessageBox messageBox;
    messageBox.setText("Successfully!");
    messageBox.setIcon(QMessageBox::Information);
    messageBox.addButton("certain", QMessageBox::AcceptRole);
    messageBox.exec();
    return true;
}

// return
void puzzle::on_pushButton_2_clicked()
{
    init_ui *ex = new init_ui();;
    ex->show();
    this->close();
}

// receive the path
void puzzle::receivePath(string s){
    cout << "接受信号" << endl;
    QString qstr2 = QString::fromStdString(s);
    originalPixmap.load(qstr2);
}

