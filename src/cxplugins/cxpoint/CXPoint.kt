package cxplugins.cxpoint

import cxplugins.cxfundamental.minecraft.file.CXYamlConfiguration
import cxplugins.cxfundamental.minecraft.kotlindsl.toColor
import cxplugins.cxfundamental.minecraft.server.CXItemStack
import cxplugins.cxfundamental.minecraft.server.CXPluginMain
import org.bukkit.Material

class CXPoint : CXPluginMain(CXItemStack(Material.GOLD_INGOT,1,"&6CXPoint","&6CXPoint提供了对于CXFundamental提供的多货币功能的货币操作命令")) {
    override fun onEnable() {
        super.onEnable()
        Messages.loadAllMessages()
        registerAllCommands()
    }
}
object Messages{
    fun loadAllMessages(){
        val configuration=CXYamlConfiguration("CXPlugins\\CXPoint","messages.yml")
        configuration.setDefault("pay.target","&6你从 <from> 处 获得了 <point> * <amount>")
        configuration.setDefault("pay.from","&6<point> * <amount> 已经转账到 <target>")
        configuration.setDefault("give.target","&6你收到了<point> * <amount>")
        configuration.setDefault("give.from","&6已经给  <target> <point> * <amount>")
        configuration.setDefault("cost.target","&6已经扣除了 <point> * <amount>")
        configuration.setDefault("cost.from","&6已经扣除了  <target> <point> * <amount>")
        configuration.setDefault("set.target","&6你的<point>已经设置为<amount>")
        configuration.setDefault("set.from","&6<target>的<point>已经设置为<amount>")
        configuration.setDefault("balance.sender","&6你目前拥有<point> * <amount>")
        configuration.saveDefault()
        configuration.save()
        configuration.load()
        Pay.target=configuration.getString("pay.target").toColor()
        Pay.from=configuration.getString("pay.from").toColor()
        Give.target=configuration.getString("give.target").toColor()
        Give.from=configuration.getString("give.from").toColor()
        Cost.target=configuration.getString("cost.target").toColor()
        Cost.from=configuration.getString("cost.from").toColor()
        Set.target=configuration.getString("set.target").toColor()
        Set.from=configuration.getString("set.from").toColor()
        Balance.sender=configuration.getString("balance.sender").toColor()
    }
    object Pay{
        lateinit var target:String
        lateinit var from:String
    }
    object Give{
        lateinit var target:String
        lateinit var from:String
    }
    object Cost{
        lateinit var target:String
        lateinit var from:String
    }
    object Set{
        lateinit var target:String
        lateinit var from:String
    }
    object Balance{
        lateinit var sender:String
    }
}