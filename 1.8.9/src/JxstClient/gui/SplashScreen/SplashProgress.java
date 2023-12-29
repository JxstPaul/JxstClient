package JxstClient.gui.SplashScreen;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;

public class SplashProgress {
	
	private static final int MAX = 8;
	private static int PROGRESS = 0;
	private static String CURRENT;
	private static ResourceLocation splash;
	private static UnicodeFontRenderer ufr;
	
	public static void update() {
		if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
			return;
		}
		drawSplash(Minecraft.getMinecraft().getTextureManager());
	}
	
	public static void setPROGRESS(int pROGRESS, String cURRENT) {
		PROGRESS = pROGRESS;
		CURRENT = cURRENT;
		update();
	}
	
	public static void drawSplash(TextureManager tm) {
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int scaleFactor = sr.getScaleFactor();
		
		Framebuffer frameBuffer = new Framebuffer(sr.getScaledWidth() * scaleFactor, sr.getScaledHeight() * scaleFactor, true);
		frameBuffer.bindFramebuffer(false);
		
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, (double)sr.getScaledWidth(), (double)sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
		
		if(splash == null) {
			splash = new ResourceLocation("jxstclient/main_menu.jpg");
		}
		
		tm.bindTexture(splash);
		
		GlStateManager.resetColor();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080, sr.getScaledWidth(), sr.getScaledHeight(), 1920, 1080);
		drawProgress();
		frameBuffer.unbindFramebuffer();
		frameBuffer.framebufferRender(sr.getScaledWidth() * scaleFactor, sr.getScaledHeight() * scaleFactor);
		
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		
		Minecraft.getMinecraft().updateDisplay();
		
	}
	
	private static void drawProgress() {
		
		if(Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
			return;
		}
		
		if(ufr == null) {
			ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
		}
		
		ScaledResolution scR = new ScaledResolution(Minecraft.getMinecraft());
		
		double nProgress = (double)PROGRESS;
		double calc = (nProgress / MAX) * scR.getScaledWidth();
		
		Gui.drawRect(0, scR.getScaledHeight() - 35, scR.getScaledWidth(), scR.getScaledHeight(), new Color(0, 0, 0, 50).getRGB());
		
		GlStateManager.resetColor();
		resetTextureState();
		
		ufr.drawStringWithShadow(CURRENT, 20, scR.getScaledHeight() - 25, new Color(255, 255, 255).getRGB());
		
		String step = PROGRESS + " / " + MAX;
		ufr.drawStringWithShadow(step, scR.getScaledWidth() - 20 - ufr.getStringWidth(step), scR.getScaledHeight() - 25, new Color(225, 225, 225).getRGB());
		
		GlStateManager.resetColor();
		resetTextureState();
		
		Gui.drawRect(0, scR.getScaledHeight() - 2, (int)calc, scR.getScaledHeight(), new Color(149, 201, 144).getRGB());
		
		Gui.drawRect(0, scR.getScaledHeight() - 2, scR.getScaledWidth(), scR.getScaledHeight(), new Color(0, 0, 0, 10).getRGB());
		
	}
	
	private static void resetTextureState() {
		GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
	}
}
