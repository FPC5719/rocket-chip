// See LICENSE.SiFive for license details.

package freechips.rocketchip.diplomacy.aop

import chisel3.Data
import org.chipsalliance.cde.config.Parameters
import freechips.rocketchip.diplomacy.InwardNode

/** Typed view of an outward edge of a diplomatic node. */
case class OutwardEdge[Bundle <: Data, EdgeOutParams](
  params: Parameters,
  bundle: Bundle,
  edge: EdgeOutParams,
  node: InwardNode[_, _, Bundle])
