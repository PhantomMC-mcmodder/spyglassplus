package com.github.teamfusion.spyglassplus.mixin.exclusive.client;

import com.github.teamfusion.spyglassplus.client.gui.BinocularsOverlayRenderer;
import com.github.teamfusion.spyglassplus.item.SpyglassPlusItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

/**
 * Replaces the inventory model for {@link SpyglassPlusItems#BINOCULARS}.
 */
@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyArgs(
        method = "renderGuiItemModel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"
        )
    )
    private void onRenderGuiItemModel(Args args) {
        Optional.ofNullable(BinocularsOverlayRenderer.modifyRenderItem(args.get(0), args.get(1))).ifPresent(model -> args.set(7, model));
    }

    @ModifyArgs(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/BuiltinModelItemRenderer;render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"
        )
    )
    private void onRenderItem(Args args) {
        Optional.ofNullable(BinocularsOverlayRenderer.modifyRenderItem(args.get(0), args.get(1))).ifPresent(model -> args.set(7, model));
    }
}
