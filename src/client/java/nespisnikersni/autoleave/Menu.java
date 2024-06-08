package nespisnikersni.autoleave;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu extends Screen {
    protected Menu(Text title) {
        super(title);
    }
    private ConfigA config = new ConfigA();
    private TextFieldWidget health;
    private ButtonWidget enabled;

    @Override
    protected void init() {
        health = new TextFieldWidget(textRenderer,width/2+10,height/2-10,40,20,null);
        enabled = ButtonWidget.builder((Text.literal(String.valueOf(config.getString("AutoLeave","enabled")))),button -> {
            if(Boolean.valueOf(config.getString("AutoLeave","enabled"))) {
                config.setValue("AutoLeave","enabled","false");
                enabled.setMessage(Text.literal(String.valueOf(config.getString("AutoLeave","enabled"))));
            }else{
                config.setValue("AutoLeave","enabled","true");
                enabled.setMessage(Text.literal(String.valueOf(config.getString("AutoLeave","enabled"))));
            }
        }).dimensions(width/2+10,height/2+10,40,20).build();
        if(config.getString("AutoLogin", "autoRegisterPassword")!=health.getText()){
            health.setText(config.getString("AutoLeave", "health"));
        }
        addDrawableChild(health);
        addDrawableChild(enabled);
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        context.drawText(textRenderer,"§fEnabled",width/2-35,height/2+15,1,true);
        context.drawText(textRenderer,"§fHealth",width/2-35,height/2-5,1,true);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER||keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            String enteredValue = health.getText();
            config.setValue("AutoLeave", "health", enteredValue);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
