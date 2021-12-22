# Day 13, in Rust

Transparent Origami. Took me a little bit to read through the instructions and make sense of them -- I had to decide if the fold line was retained, for instance.

Part 1 went reasonably smoothly, although it still takes me longer to write code in Rust than it does in some other languages.

Part 2 was a bit more of a battle with the borrow checker for me. I wanted to iterate over the final vec several times, and didn't want to clone it, so I had to work in some references and the like. That stuff still doesn't come naturally to me with Rust, likely because I don't spend enough time in Rust. Still, I enjoy writing Rust, even though I also struggle with parts of it, and the resulting solution is fast and safe.

I suspect it could still be more idiomatic, but I'm happy with it for now.

## Tools

CLion again. Still feels like a sub-par experience compared to most JetBrains tools. Won't optimize imports, even -- so if you import something and realize (or are warned) that it's not being used, you have to remove that yourself. There's a reason for that, apparently, but these feel like table stakes for a decent editor for me. It's better than nothing, but feels like early days still, and if someone told me their IDE was vastly better for Rust, I'd certainly try it.
 