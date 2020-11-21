package com.kotori316.scala_lib

import net.minecraftforge.fml.common.Mod

@Mod(SLPMixin.MOD_ID)
object SLPMixin {
  final val MOD_ID = "slp-mixin"
  SLPTest.registerTest()
}
