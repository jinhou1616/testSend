package messages



import com.rxzy.utils.*
import grails.transaction.Transactional

@Transactional
class SendUrlService {

    public  int sendSMS(String mobiles, String smsContent) {
        def var = Utils.sendPostRequestByForm("http://sms.xjmei.com/send",smsContent,mobiles);
//        println(var);
        return var;
    }


    def serviceMethod() {

    }
}
