# Day 10 in TypeScript

I wrote Day 10 in TypeScript. I've been using more ES6 than TS lately, so I had to remind myself of a few things:
- Map type (w/ `has`/`get`/`keys`)
- When I felt like implicit and explicit typing was useful

## Tools

I used WebStorm. Since node doesn't run TS, had to:
- Install node bindings
- Set up a JS / Node run config
- Set up a compile step for pre-launch on the run config
- Configure WebStorm to automatically compile TS

I briefly looked at VS Code, and it's certainly viable but has some of the same issues -- setting up compilation and run configs for TypeScript is a little more work than when you're using JS directly.
