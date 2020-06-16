package voronoiaoc.byg.common.world.feature.features.overworld.trees.bayou;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import voronoiaoc.byg.core.byglists.BYGBlockList;

import java.util.Random;
import java.util.function.Function;

import static net.minecraft.util.Direction.*;

public class BayouTree3 extends Feature<NoFeatureConfig> {
    public static boolean doBlockNotify;

    public BayouTree3(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int randChance = rand.nextInt(2);
        int randTreeHeight = rand.nextInt(6) + 5;
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        BlockPos.Mutable block = new BlockPos.Mutable(blockPos);
        BlockPos.Mutable mainMutable = new BlockPos.Mutable(block);

        if (!this.checkArea(worldIn, pos, 5) || worldIn.getBlockState(pos.down()).getBlock() != Blocks.DIRT && worldIn.getBlockState(pos.down()).getBlock() != BYGBlockList.MUD_BLOCK && worldIn.getBlockState(pos.down()).getBlock() != Blocks.GRASS_BLOCK) {
            return false;
        } else {
            //Trunk
            for (int i = 3; i <= randTreeHeight; i++) {
                BlockPos.Mutable mutable = new BlockPos.Mutable(block);
                this.setWillowLog(worldIn, mutable.move(UP, i));
            }

            for (int baseSize = 0; baseSize < 4; baseSize++) {
                BlockPos.Mutable mutable = new BlockPos.Mutable(block.up(3));
                for (Direction direction : Plane.HORIZONTAL) {
                    mutable.setPos(block.up(3).offset(direction, baseSize));
                if (worldIn.getBlockState(mutable).getBlock() != Blocks.DIRT)
                    this.setWillowLog(worldIn, mutable.move(DOWN, baseSize));
                }
            }
            this.setWillowLog(worldIn, mainMutable.move(UP, randTreeHeight - 1).move(SOUTH).move(EAST));
            this.setWillowLog(worldIn, mainMutable.move(DOWN));
            this.setWillowLog(worldIn, mainMutable.move(UP, 2).move(EAST));
            mainMutable.setPos(block);
            this.setWillowLog(worldIn, mainMutable.move(UP, randTreeHeight - 1).move(WEST));
            this.setWillowLog(worldIn, mainMutable.move(WEST).move(UP).move(NORTH));
            mainMutable.setPos(block);
            this.setWillowLog(worldIn, mainMutable.move(UP, randTreeHeight - 2).move(NORTH));
            this.setWillowLog(worldIn, mainMutable.move(UP).move(NORTH));
            mainMutable.setPos(block);

//            this.setWillowLeaves(worldIn, mainMutable.move(UP, randTreeHeight));
//            for (int placeX = -3; placeX <= 3; placeX++) {
//                for (int placeZ = -3; placeZ <= 3; placeZ++) {
//                    if (placeX <= 2 && placeX >= -2 && placeZ <= 2 && placeZ >= -2) {
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 0, placeZ));
//                        this.setWillowLeaves(worldIn, mainMutable.add(3, 0, placeZ));
//                        this.setWillowLeaves(worldIn, mainMutable.add(-3, 0, placeZ));
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 0, 3));
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 0, -3));
//
//                        for (int placeY = -1; placeY >= -(rand.nextInt(4) + 2); placeY--) {
//                            this.setWillowLeaves(worldIn, mainMutable.add(4, placeY, placeZ));
//                            this.setWillowLeaves(worldIn, mainMutable.add(-4, placeY, placeZ));
//                            this.setWillowLeaves(worldIn, mainMutable.add(placeX, placeY, 4));
//                            this.setWillowLeaves(worldIn, mainMutable.add(placeX, placeY, -4));
//                            this.setWillowLeaves(worldIn, mainMutable.add(3, placeY, 3));
//                            this.setWillowLeaves(worldIn, mainMutable.add(-3, placeY, 3));
//                            this.setWillowLeaves(worldIn, mainMutable.add(3, placeY, -3));
//                            this.setWillowLeaves(worldIn, mainMutable.add(-3, placeY, -3));
//                        }
//                    }
//
//                    if (placeX <= 1 && placeX >= -1 && placeZ <= 1 && placeZ >= -1) {
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 1, placeZ));
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 1, 2));
//                        this.setWillowLeaves(worldIn, mainMutable.add(placeX, 1, -2));
//                        this.setWillowLeaves(worldIn, mainMutable.add(2, 1, placeZ));
//                        this.setWillowLeaves(worldIn, mainMutable.add(-2, 1, placeZ));
//                        if (randChance == 0) {
//                            this.setWillowLeaves(worldIn, mainMutable.add(placeX, 0, 4));
//                            this.setWillowLeaves(worldIn, mainMutable.add(placeX, 0, -4));
//                            this.setWillowLeaves(worldIn, mainMutable.add(4, 0, placeZ));
//                            this.setWillowLeaves(worldIn, mainMutable.add(-4, 0, placeZ));
//                        }
//                    }
//                }
//            }
        }
        return true;
    }

    protected void setWillowLog(IWorldWriter worldIn, BlockPos pos) {
            this.setBlockStateWithoutUpdates(worldIn, pos, BYGBlockList.WILLOW_LOG.getDefaultState());
    }

    protected void setWillowLeaves(IWorldWriter worldIn, BlockPos pos) {
        if ((worldIn instanceof IWorld)) {
            if (((IWorld) worldIn).getBlockState(pos).getBlock() != BYGBlockList.WILLOW_LOG)
                this.setBlockStateWithoutUpdates(worldIn, pos, BYGBlockList.WILLOW_LEAVES.getDefaultState().with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(1)));
        }
    }

    private void setBlockStateWithoutUpdates(IWorldWriter worldWriter, BlockPos blockPos, BlockState blockState) {
        worldWriter.setBlockState(blockPos, blockState, 19);
    }

    private boolean checkArea(IWorld world, BlockPos pos, int radius) {
        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();

        for (int checkX = -radius; checkX <= radius; checkX++) {
            for (int checkZ = -radius; checkZ <= radius; checkZ++) {
                BlockPos checkArea = new BlockPos(posX + checkX, posY, posZ + checkZ);

                if (world.getBlockState(checkArea).getBlock() == BYGBlockList.WILLOW_LOG) return false;

            }
        }
        return true;
    }
}