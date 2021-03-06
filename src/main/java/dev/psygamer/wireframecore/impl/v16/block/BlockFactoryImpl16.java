package dev.psygamer.wireframecore.impl.v16.block;

import dev.psygamer.wireframecore.impl.ImplementationVersion;
import dev.psygamer.wireframecore.impl.MinecraftVersion;
import dev.psygamer.wireframecore.impl.common.block.CommonBlockFactory;
import dev.psygamer.wireframe.block.BlockFactory;
import dev.psygamer.wireframe.block.BlockWrapper;
import dev.psygamer.wireframe.block.properties.HarvestLevel;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@ImplementationVersion(MinecraftVersion.v16)
public class BlockFactoryImpl16 extends CommonBlockFactory {
	
	public BlockFactoryImpl16(final String registryName) {
		super(registryName);
	}
	
	public static BlockFactory create(final String registryName) {
		return new BlockFactoryImpl16(registryName);
	}
	
	@Override
	@SuppressWarnings("ConstantConditions")
	public BlockFactory inheritFromBlock(final Block block) {
		final AbstractBlock.Properties properties = ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.class, block, "field_235684_aB_");
		final Class<AbstractBlock.Properties> propertiesClass = AbstractBlock.Properties.class;
		
		assert properties != null;
		
		setMaterial(ObfuscationReflectionHelper.getPrivateValue(propertiesClass, properties, "field_200953_a"));
		setSound(ObfuscationReflectionHelper.getPrivateValue(propertiesClass, properties, "field_200956_d"));
		setGroup(block.asItem().getItemCategory());
		
		setHardness(ObfuscationReflectionHelper.getPrivateValue(propertiesClass, properties, "field_200959_g"));
		setBlastResistance(ObfuscationReflectionHelper.getPrivateValue(propertiesClass, properties, "field_200958_f"));
		
		setRequiredTool(properties.getHarvestTool());
		setHarvestLevel(properties.getHarvestLevel());
		
		setBreakableByHand(!(Boolean) ObfuscationReflectionHelper.getPrivateValue(propertiesClass, properties, "field_235806_h_"));
		
		return this;
	}
	
	@Override
	public Block build() {
		final BlockWrapper wrapper = BlockWrapper.create(this);
		
		return wrapper.getBlock();
	}
	
	@Override
	public Block createBlock() {
		return new Block(createProperties()) {
		
		};
	}
	
	private AbstractBlock.Properties createProperties() {
		final AbstractBlock.Properties properties = AbstractBlock.Properties.of(this.material);
		
		if (this.hardness > 0 && this.blastResistance > 0) {
			properties.strength(this.hardness, this.blastResistance);
		} else if (this.hardness > 0) {
			properties.strength(this.hardness);
		}
		
		if (this.requiredTool == null) {
			properties.instabreak();
		} else {
			properties.harvestTool(this.requiredTool);
		}
		
		if (this.harvestLevel != HarvestLevel.HAND) {
			properties.harvestLevel(this.harvestLevel.getLevel());
			
			if (this.breakableByHand) {
				properties.requiresCorrectToolForDrops();
			}
		}
		
		if (this.sound != null) {
			properties.sound(this.sound);
		}
		
		if (this.fullBlock && !this.opaque) {
			properties.isValidSpawn(AbstractBlock.AbstractBlockState::isValidSpawn);
			properties.isSuffocating(AbstractBlock.AbstractBlockState::isSuffocating);
			properties.isViewBlocking(AbstractBlock.AbstractBlockState::isViewBlocking);
			properties.noCollission();
		}
		
		return properties;
	}
}
