package zynq

import chisel3._
import freechips.rocketchip.config.{Parameters, Config}
import freechips.rocketchip.subsystem._
import freechips.rocketchip.system.WithJtagDTMSystem
import freechips.rocketchip.devices.tilelink.BootROMParams
import freechips.rocketchip.rocket.{RocketCoreParams, MulDivParams, DCacheParams, ICacheParams}
import freechips.rocketchip.tile.{RocketTileParams, XLen}
import testchipip._

class WithBootROM extends Config((site, here, up) => {
  case BootROMParams => BootROMParams(
    contentFileName = s"../testchipip/bootrom/bootrom.rv${site(XLen)}.img")
})

class WithZynqAdapter extends Config((site, here, up) => {
  case SerialFIFODepth => 16
  case ResetCycles => 10
  case ZynqAdapterBase => BigInt(0x43C00000L)
  case ExtMem => up(ExtMem, site).map(x => x.copy(master=x.master.copy(idBits = 6)))
  case ExtIn => up(ExtIn, site).map(_.copy(beatBytes = 4, idBits = 12))
  case BlockDeviceKey => BlockDeviceConfig(nTrackers = 2)
  case BlockDeviceFIFODepth => 16
  case NetworkFIFODepth => 16
})

class WithNMediumCores(n: Int) extends Config((site, here, up) => {
  case RocketTilesKey => {
    val medium = RocketTileParams(
      core = RocketCoreParams(fpu = None),
      btb = None,
      dcache = Some(DCacheParams(
        rowBits = site(SystemBusKey).beatBytes*8,
        nSets = 64,
        nWays = 1,
        nTLBEntries = 4,
        nMSHRs = 0,
        blockBytes = site(CacheBlockBytes))),
      icache = Some(ICacheParams(
        rowBits = site(SystemBusKey).beatBytes*8,
        nSets = 64,
        nWays = 1,
        nTLBEntries = 4,
        blockBytes = site(CacheBlockBytes))))
    List.tabulate(n)(i => medium.copy(hartId = i))
  }
})

class DefaultConfig extends Config(new WithJtagDTMSystem ++ new WithBootROM ++ new freechips.rocketchip.system.BaseConfig)
class DefaultMediumConfig extends Config(
  new WithBootROM ++
  new freechips.rocketchip.system.BaseConfig)
class DefaultSmallConfig extends Config(
  new WithBootROM ++ new freechips.rocketchip.system.DefaultSmallConfig)

class ZynqConfig extends Config(new WithNMonotonicInterruptorSources(1) ++ new WithoutTLMonitors ++ new WithZynqAdapter ++ new DefaultConfig)
class ZynqMediumConfig extends Config(new WithoutTLMonitors ++ new WithZynqAdapter ++ new DefaultMediumConfig)
class ZynqSmallConfig extends Config(new WithoutTLMonitors ++ new WithZynqAdapter ++ new DefaultSmallConfig)

class ZynqFPGAConfig extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(5) ++ new ZynqConfig)
class ZynqMediumFPGAConfig extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(5) ++ new ZynqMediumConfig)
class ZynqSmallFPGAConfig extends Config(new WithoutVM ++ new WithNMediumCores(1) ++ new WithNExtTopInterrupts(5) ++ new WithoutTLMonitors ++ new ZynqSmallConfig)

class EvalConfigH2I8 extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(5) ++ new ZynqConfig)
class EvalConfigH2I16 extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(13) ++ new ZynqConfig)
class EvalConfigH2I32 extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(29) ++ new ZynqConfig)
class EvalConfigH2I64 extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(61) ++ new ZynqConfig)
class EvalConfigH2I128 extends Config(new WithoutVM ++ new WithNMediumCores(2) ++ new WithNExtTopInterrupts(125) ++ new ZynqConfig)

class EvalConfigH4I8 extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(5) ++ new ZynqConfig)
class EvalConfigH4I16 extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(13) ++ new ZynqConfig)
class EvalConfigH4I32 extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(29) ++ new ZynqConfig)
class EvalConfigH4I64 extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(61) ++ new ZynqConfig)
class EvalConfigH4I128 extends Config(new WithoutVM ++ new WithNMediumCores(4) ++ new WithNExtTopInterrupts(125) ++ new ZynqConfig)

class EvalConfigH8I8 extends Config(new WithoutVM ++ new WithNMediumCores(8) ++ new WithNExtTopInterrupts(5) ++ new ZynqConfig)
class EvalConfigH8I16 extends Config(new WithoutVM ++ new WithNMediumCores(8) ++ new WithNExtTopInterrupts(13) ++ new ZynqConfig)
class EvalConfigH8I32 extends Config(new WithoutVM ++ new WithNMediumCores(8) ++ new WithNExtTopInterrupts(29) ++ new ZynqConfig)
class EvalConfigH8I64 extends Config(new WithoutVM ++ new WithNMediumCores(8) ++ new WithNExtTopInterrupts(61) ++ new ZynqConfig)
class EvalConfigH8I128 extends Config(new WithoutVM ++ new WithNMediumCores(8) ++ new WithNExtTopInterrupts(125) ++ new ZynqConfig)

class EvalConfigH16I8 extends Config(new WithoutVM ++ new WithNMediumCores(16) ++ new WithNExtTopInterrupts(5) ++ new ZynqConfig)
class EvalConfigH16I16 extends Config(new WithoutVM ++ new WithNMediumCores(16) ++ new WithNExtTopInterrupts(13) ++ new ZynqConfig)
class EvalConfigH16I32 extends Config(new WithoutVM ++ new WithNMediumCores(16) ++ new WithNExtTopInterrupts(29) ++ new ZynqConfig)
class EvalConfigH16I64 extends Config(new WithoutVM ++ new WithNMediumCores(16) ++ new WithNExtTopInterrupts(61) ++ new ZynqConfig)
class EvalConfigH16I128 extends Config(new WithoutVM ++ new WithNMediumCores(16) ++ new WithNExtTopInterrupts(125) ++ new ZynqConfig)
