package nespisnikersni.autoleave;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return parent -> {
                Menu configMenuScreen = new Menu(Text.literal("seg"));
                return configMenuScreen;
            };
    }
}
