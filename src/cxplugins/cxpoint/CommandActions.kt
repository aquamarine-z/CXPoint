package cxplugins.cxpoint
import cxplugins.cxfundamental.minecraft.command.CPMLCommandExecutor.Companion.register
import cxplugins.cxfundamental.minecraft.kotlindsl.sendMessageWithColor
import cxplugins.cxfundamental.minecraft.server.CXMessage
import cxplugins.plugins.cxpoint.CXEconomy
import org.bukkit.entity.Player

fun registerAllCommands(){
    register("cxpoint help"){
        action{
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint list ��ȡ�Ѿ������Ļ����б�")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint cost <�����> <������> <����> ��<���>�۳�<����>��<������>")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint give <�����> <������> <����> ��<���> <����>��<������>")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint set <�����> <������> <����> ����<���> <������> ������")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint create <������> ����һ�ֻ���")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint remove <������> ɾ��һ�ֻ���")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint setpayable <������> true/false �������ֻ����ܷ����ʹ��/ppayָ��ת��")
            sender.sendMessageWithColor("&6[CXPoint]:/pbal <������> ��ȡ��ӵ��<������>������")
            sender.sendMessageWithColor("&6[CXPoint]:/ppay <�����> <������> <���> ��<�����> ת�� <������> <���> Ԫ")
            true
        }
    }
    register("cxpoint give"){
        parameter {
            onlinePlayer {
                name="target"
            }
            string {
                name="name"
            }
            double{
                name="amount"
            }
        }
        action {
            val target=if(onlinePlayers["target"]!=null)onlinePlayers["target"]else{
                return@action true
            }
            if(!CXEconomy.hasPoint(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            CXEconomy.give(target!!,strings["name"]!!,doubles["amount"]!!)
            target.sendMessageWithColor(Messages.Give.target.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            sender.sendMessageWithColor(Messages.Give.from.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            true
        }
    }
    register("cxpoint set"){
        parameter {
            onlinePlayer {
                name="target"
            }
            string {
                name="name"
            }
            double{
                name="amount"
            }
        }
        action{
            val target = if (onlinePlayers["target"] != null) onlinePlayers["target"] else {
                return@action true
            }
            if (!CXEconomy.hasPoint(strings["name"]!!)) {
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            CXEconomy.set(target!!, strings["name"]!!, doubles["amount"]!!)
            target.sendMessageWithColor(Messages.Set.target.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            sender.sendMessageWithColor(Messages.Set.from.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            true
        }
    }
    register("cxpoint cost"){
        parameter {
            onlinePlayer {
                name="target"
            }
            string {
                name="name"
            }
            double{
                name="amount"
            }
        }
        action {
            val target=if(onlinePlayers["target"]!=null)onlinePlayers["target"]else{
                return@action true
            }
            if(!CXEconomy.hasPoint(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            CXEconomy.cost(target!!,strings["name"]!!,doubles["amount"]!!)
            target.sendMessageWithColor(Messages.Cost.target.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            sender.sendMessageWithColor(Messages.Cost.from.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            true
        }
    }
    register("cxpoint setpayable"){
        parameter {
            string {
                name="name"
            }
            boolean {
                name="state"
            }
        }
        action {
            if(!CXEconomy.hasPoint(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            CXEconomy.setPayable(strings["name"]!!,booleans["state"]!!)
            sender.sendMessageWithColor("&6[CXPoint]�Ѿ�������${strings["name"]}���ܷ�֧��Ϊ${booleans["state"]}")
            true
        }
    }
    register("cxpoint create"){
        parameter {
            string {
                name="name"
            }
        }
        action {
            if(CXEconomy.hasPoint(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]${strings["name"]}�Ѵ���!")
                return@action true
            }
            CXEconomy.create(strings["name"]!!,false)
            sender.sendMessageWithColor("&6[CXPoint]�Ѿ�������${strings["name"]}")
            true
        }
    }
    register("cxpoint remove"){
        parameter {
            string {
                name="name"
            }
        }
        action {
            if(!CXEconomy.hasPoint(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]${strings["name"]}������!")
                return@action true
            }
            CXEconomy.delete(strings["name"]!!)
            sender.sendMessageWithColor("&6[CXPoint]�Ѿ�ɾ����${strings["name"]}")
            true
        }
    }
    register("cxpoint list"){
        action {
            val list=CXEconomy.pointNameList
            for((i,point) in list.withIndex()){
                sender.sendMessageWithColor("&6${i+1}.����:$point �ܷ�֧��:${CXEconomy.getPayable(point)}")
            }
            true
        }
    }
    register("pbal"){
        parameter {
            string {
                name="name"
            }
        }
        action {
            if(sender !is Player){
                sender.sendMessageWithColor("&4[����]������������������ִ��")
                return@action true
            }
            if (!CXEconomy.hasPoint(strings["name"]!!)) {
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            val amount=CXEconomy.get(sender as Player,strings["name"]!!)
            sender.sendMessageWithColor(Messages.Balance.sender.replace("<amount>","$amount").replace("<point>",strings["name"]!!))
            true
        }
    }
    register("ppay"){
        parameter {
            onlinePlayer {
                name="target"
            }
            string {
                name="name"
            }
            double{
                name="amount"
            }
        }
        action{
            if(sender !is Player){
                sender.sendMessageWithColor("&4[����]������������������ִ��")
                return@action true
            }
            val target = if (onlinePlayers["target"] != null) onlinePlayers["target"] else {
                return@action true
            }
            if (!CXEconomy.hasPoint(strings["name"]!!)) {
                sender.sendMessageWithColor("&4[����]�޷��ҵ�����${strings["name"]}�Ļ���")
                return@action true
            }
            if(!CXEconomy.getPayable(strings["name"]!!)){
                sender.sendMessageWithColor("&4[����]${strings["name"]}�޷�֧��")
                return@action true
            }
            CXEconomy.pay(sender as Player,target!!, strings["name"]!!, doubles["amount"]!!)

            target.sendMessageWithColor(Messages.Pay.target.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            sender.sendMessageWithColor(Messages.Pay.from.replace("<amount>","${doubles["amount"]}").replace("<target>",
                target.name).replace("<from>",sender.name).replace("<point>",strings["name"]!!))
            true
        }
    }
}