package nespisnikersni.autoleave;

import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class AutoLeaveClient implements ClientModInitializer {
	private ConfigA config = new ConfigA();
	public static KeyBinding keyBinding;
	@Override
	public void onInitializeClient() {
		config.createConfig("AutoLeave");
		registerModMenuIntegration();
		if(config.isEmpty("AutoLeave")){
			config.addString("AutoLeave","enabled",String.valueOf(true));
			config.addString("AutoLeave","health","5");
		}
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "AutoLeave"));
		ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEvents.EndTick() {
			@Override
			public void onEndTick(MinecraftClient client) {
                if(client.player != null&&client.world!=null&&Boolean.valueOf(config.getString("AutoLeave","enabled"))&&client.player.getHealth()<=Integer.valueOf(config.getString("AutoLeave","health"))){
					client.world.disconnect();
				}
				while(keyBinding.wasPressed()){
					Menu menu = new Menu(Text.literal("aesf"));
					client.setScreen(menu);
				}
			}
		});
	}
	public static void registerModMenuIntegration() {
		if (FabricLoader.getInstance().isModLoaded("modmenu")) {
			ModMenuApi modMenuApi = new ModMenuIntegration();
		}
	}
}