// See LICENSE.SiFive for license details.

package freechips.rocketchip.diplomacy.aop

import chisel3.Data
import freechips.rocketchip.diplomacy.{AnyMixedNode, BaseNode, LazyModule, MixedNode}

/** Combinators for inspecting diplomatic nodes and LazyModule hierarchies. */
object Select {
  private def getInwardEdges[BI <: Data, EI](
    node: MixedNode[_, _, EI, BI, _, _, _, _ <: Data]
  ): Iterable[InwardEdge[BI, EI]] = {
    node.iPorts.zip(node.in).map { case ((_, upstream, params, _), (bundle, edge)) =>
      InwardEdge(params, bundle, edge, upstream)
    }
  }

  /** Collect values from every inward edge of `node`. */
  def collectInwardEdges[T](node: BaseNode)(
    collect: PartialFunction[InwardEdge[_ <: Data, _], T]
  ): Iterable[T] = node match {
    case mixed: AnyMixedNode => getInwardEdges(mixed).collect(collect)
    case _                   => Iterable.empty
  }

  private def getOutwardEdges[BO <: Data, EO](
    node: MixedNode[_, _, _, _ <: Data, _, _, EO, BO]
  ): Iterable[OutwardEdge[BO, EO]] = {
    node.oPorts.zip(node.out).map { case ((_, downstream, params, _), (bundle, edge)) =>
      OutwardEdge(params, bundle, edge, downstream)
    }
  }

  /** Collect values from every outward edge of `node`. */
  def collectOutwardEdges[T](node: BaseNode)(
    collect: PartialFunction[OutwardEdge[_ <: Data, _], T]
  ): Iterable[T] = node match {
    case mixed: AnyMixedNode => getOutwardEdges(mixed).collect(collect)
    case _                   => Iterable.empty
  }

  /** Recursively collect from a LazyModule and all descendants. */
  def collectDeep[T](lmod: LazyModule)(collect: PartialFunction[LazyModule, T]): Iterable[T] =
    collect.lift(lmod) ++ lmod.getChildren.flatMap(collectDeep(_)(collect))

  /**
    * Recursively collect while `filter` accepts a module.  Rejected modules
    * and their subtrees are skipped.
    */
  def filterCollectDeep[T](lmod: LazyModule)(filter: LazyModule => Boolean)(
    collect: PartialFunction[LazyModule, T]
  ): Iterable[T] = {
    if (filter(lmod)) collect.lift(lmod) ++ lmod.getChildren.flatMap(filterCollectDeep(_)(filter)(collect))
    else Iterable.empty
  }
}
