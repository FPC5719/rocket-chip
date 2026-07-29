// See LICENSE.SiFive for license details.

package freechips.rocketchip.diplomacy

import freechips.rocketchip.macros.ValNameImpl

/**
  * Captures the source-level name assigned to a diplomatic object.
  *
  * `name` is the historical Rocket Chip API.  `value` mirrors the standalone
  * Diplomacy API (which uses `sourcecode.Name`) so code ported from that
  * library can be used without changing the legacy naming materializer.
  */
case class ValName(name: String) {
  def value: String = name
}

object ValName
{
  implicit def materialize(implicit x: ValNameImpl): ValName = ValName(x.name)
}
