package testsend

class SendController {
//定义服务
    def SendUrlService;


    def send(){
//调用发送服务发送短信
        def var = SendUrlService.sendSMS("18009908980","融信智有短信测试");
        println(var);
        render ("success!");
    }

    def index() {
        send();
    }
}
