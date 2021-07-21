package dev.psygamer.wireframe.block;

import dev.psygamer.wireframe.core.impl.handle.Implementor;
import dev.psygamer.wireframe.core.namespace.Namespace;
import dev.psygamer.wireframe.Wireframe;
import net.minecraft.block.Block;

import java.util.List;


public interface BlockWrapper {
	
	static BlockWrapper create(final BlockFactory factory) {
		return Implementor.execute(factory);
	}
	
	Namespace getNamespace();
	
	String getRegistryName();
	
	Block getBlock();
	
	BlockFactory getFactory();
	
	boolean hasBlockVariants();
	
	List<BlockWrapper> getBlockVariants();
	
	List<BlockProperty<?>> getBlockProperties();
	
}