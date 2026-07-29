// See LICENSE.SiFive for license details.

package freechips.rocketchip.diplomacy.aop

import chisel3.Data
import org.chipsalliance.cde.config.Parameters
import freechips.rocketchip.diplomacy.OutwardNode

/** Typed view of an inward edge of a diplomatic node. */
case class InwardEdge[Bundle <: Data, EdgeInParams](
  params: Parameters,
  bundle: Bundle,
  edge: EdgeInParams,
  node: OutwardNode[_, _, Bundle])
