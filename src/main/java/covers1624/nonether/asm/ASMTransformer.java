package covers1624.nonether.asm;

import codechicken.lib.asm.ASMHelper;
import codechicken.lib.asm.ObfMapping;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created by covers1624 on 5/18/2016.
 */
public class ASMTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!name.equals("net.minecraft.block.BlockPortal")){
            return bytes;
        }

        ClassNode classNode = ASMHelper.createClassNode(bytes);
        ObfMapping mapping = new ObfMapping("net/minecraft/block/BlockPortal", "func_150000_e", "(Lnet/minecraft/world/World;III)Z");
        MethodNode methodNode = ASMHelper.findMethod(mapping, classNode);
        if (methodNode == null){
            FMLLog.log(Level.ERROR, "Unable to find method node for Portal placement!");
            return bytes;
        }
        FMLLog.log(Level.INFO, "Replacing method Instructions in BlockPortal...");

        methodNode.instructions.clear();
        methodNode.localVariables.clear();
        methodNode.tryCatchBlocks.clear();
        methodNode.instructions.add(new InsnNode(Opcodes.ICONST_0));
        methodNode.instructions.add(new InsnNode(Opcodes.IRETURN));

        FMLLog.log(Level.INFO, "Instructions replaced.");
        return ASMHelper.createBytes(classNode, 0);
    }
}
