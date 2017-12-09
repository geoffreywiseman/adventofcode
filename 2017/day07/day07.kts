import java.io.File


data class TreeNode(val name: String, var weight: Int? = null, var children: Set<TreeNode>? = null) {
	val complete: Boolean
		get() {
			return weight != null && children != null
		}

	val combinedWeight: Int
		get() {
			assert(complete)
			val childrenWeight = children!!.fold(0) { sum, child -> sum + child.combinedWeight }
			return weight!! + childrenWeight
		}

	override fun hashCode(): Int {
		return name.hashCode();
	}

	fun fixBalance(adding: Int) {
		print("Adding $adding to $name's weight ($weight) to fix balance.")
		weight = weight!!.plus(adding)
	}

	fun rebalance(adding: Int? = null) {
		val children = children;
		if (children == null || children.isEmpty()) {
			if (adding != null) {
				fixBalance(adding)
				return;
			}
			return;
		}

		val byCombined = children.groupBy { it.combinedWeight }
		if (byCombined.size == 1) {
			val balance = byCombined.entries.first()
			if (adding != null) {
				fixBalance(adding)
				return
			} else {
				print("$name is balanced, all ${children.size} subtowers have a combined weight of ${balance.key}.\n")
				children.forEach { it.rebalance() }
			}
		} else if (byCombined.size == 2) {
			val first = byCombined.entries.first()
			val last = byCombined.entries.last()
			if (first.value.size == 1 && last.value.size > 1) {
				rebalanceChild(first.value.first(), adding, last.key - first.key)
			} else if (last.value.size == 1 && first.value.size > 1) {
				rebalanceChild(last.value.first(), adding, first.key - last.key)
			} else {
				throw IllegalStateException("Cannot balance tower -- subtrees weights aren't 1 && >1: ${byCombined}")
			}
		} else {
			throw IllegalStateException("Cannot balance tower -- subtrees have more than two weights: ${byCombined}")
		}
	}

	fun rebalanceChild(unbalancedChild: TreeNode, adding: Int?, childAdding: Int) {
		if (adding != null && childAdding != adding) {
			throw IllegalStateException("Child is unbalanced by $childAdding but already trying to balance by $adding\n")
		} else {
			unbalancedChild.rebalance(childAdding)
		}
	}
}

class TreeParser(val filename: String) {
	companion object {
		val linePattern = Regex("([a-z]+) \\((\\d+)\\)( -> ([a-z]+, )*[a-z]+)?\\s*")
		val childrenPattern = Regex(" ([a-z]+)(,|$)")
	}


	val roots = mutableSetOf<TreeNode>();
	val nodes = mutableSetOf<TreeNode>();
	val nodesByName = mutableMapOf<String, TreeNode>()

	fun parse(): TreeNode {
		File(filename).readLines().forEach(this::parseLine)
		if (!nodes.all { it.complete })
			throw IllegalStateException("Finished parsing file ($filename) but ended up with incomplete nodes: ${nodes.filterNot { it.complete }}")
		else if (roots.size != 1)
			throw IllegalStateException("Finished parsing file ($filename) but did not end up with a single root node: ${roots}")
		else
			return roots.first()
	}

	fun parseLine(line: String) {
		val match = linePattern.matchEntire(line)
		if (match == null) {
			throw IllegalArgumentException("Could not parse line: '$line'")
		}

		val name = match.groupValues[1]
		val weight = match.groupValues[2].toInt()
		val children = parseChildren(match.groupValues[3])

		var node = nodesByName[name]
		if (node == null) {
			node = TreeNode(name, weight, children)
			nodes.add(node)
			roots.add(node)
			nodesByName[name] = node
		} else {
			node.weight = weight;
			node.children = children;
		}
	}

	fun parseChildren(text: String): Set<TreeNode> {
		if (!text.isBlank()) {
			return childrenPattern.findAll(text).map { match ->
				val name = match.groupValues[1]
				getChild(name)
			}.toSet()
		} else {
			return setOf<TreeNode>();
		}
	}

	private fun getChild(name: String): TreeNode {
		var child = nodesByName[name]
		if (child == null) {
			child = TreeNode(name)
			nodes.add(child)
			nodesByName[name] = child
		} else {
			roots.remove(child)
		}
		return child
	}
}

val root = TreeParser("day07-input.txt").parse()
print("Root node: ${root.name} has combined weight of ${root.combinedWeight}\n")

root.rebalance();