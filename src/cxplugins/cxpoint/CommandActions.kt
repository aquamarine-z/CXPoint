package cxplugins.cxpoint
import cxplugins.cxfundamental.minecraft.command.CPMLCommandExecutor.Companion.register
import cxplugins.cxfundamental.minecraft.kotlindsl.sendMessageWithColor
import cxplugins.cxfundamental.minecraft.server.CXMessage
import cxplugins.plugins.cxpoint.CXEconomy
import org.bukkit.entity.Player

fun registerAllCommands(){
    register("cxpoint help"){
        action{
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint list 获取已经创建的货币列表")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint cost <玩家名> <货币名> <数量> 从<玩家>扣除<数量>的<货币名>")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint give <玩家名> <货币名> <数量> 给<玩家> <数量>的<货币名>")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint set <玩家名> <货币名> <数量> 设置<玩家> <货币名> 的数量")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint create <货币名> 创建一种货币")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint remove <货币名> 删除一种货币")
            sender.sendMessageWithColor("&6[CXPoint]:/CXPoint setpayable <货币名> true/false 设置这种货币能否被玩家使用/ppay指令转账")
            sender.sendMessageWithColor("&6[CXPoint]:/pbal <货币名> 获取你拥有<货币名>的数量")
            sender.sendMessageWithColor("&6[CXPoint]:/ppay <玩家名> <货币名> <金额> 向<玩家名> 转账 <货币名> <金额> 元")
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
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
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
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
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
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
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
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
                return@action true
            }
            CXEconomy.setPayable(strings["name"]!!,booleans["state"]!!)
            sender.sendMessageWithColor("&6[CXPoint]已经设置了${strings["name"]}的能否支付为${booleans["state"]}")
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
                sender.sendMessageWithColor("&4[错误]${strings["name"]}已存在!")
                return@action true
            }
            CXEconomy.create(strings["name"]!!,false)
            sender.sendMessageWithColor("&6[CXPoint]已经创建了${strings["name"]}")
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
                sender.sendMessageWithColor("&4[错误]${strings["name"]}不存在!")
                return@action true
            }
            CXEconomy.delete(strings["name"]!!)
            sender.sendMessageWithColor("&6[CXPoint]已经删除了${strings["name"]}")
            true
        }
    }
    register("cxpoint list"){
        action {
            val list=CXEconomy.pointNameList
            for((i,point) in list.withIndex()){
                sender.sendMessageWithColor("&6${i+1}.名字:$point 能否支付:${CXEconomy.getPayable(point)}")
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
                sender.sendMessageWithColor("&4[错误]此命令必须由在线玩家执行")
                return@action true
            }
            if (!CXEconomy.hasPoint(strings["name"]!!)) {
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
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
                sender.sendMessageWithColor("&4[错误]此命令必须由在线玩家执行")
                return@action true
            }
            val target = if (onlinePlayers["target"] != null) onlinePlayers["target"] else {
                return@action true
            }
            if (!CXEconomy.hasPoint(strings["name"]!!)) {
                sender.sendMessageWithColor("&4[错误]无法找到叫做${strings["name"]}的货币")
                return@action true
            }
            if(!CXEconomy.getPayable(strings["name"]!!)){
                sender.sendMessageWithColor("&4[错误]${strings["name"]}无法支付")
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