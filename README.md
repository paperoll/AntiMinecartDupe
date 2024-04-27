# AntiMinecartDupe
## qna:

### what is this?
- it is a patch i wrote that prevents the basic minecart chest dupe (as seen here: <https://youtu.be/vbttUVUqIbU>)

### how does it work?
- we basically just listen for right clicks on storage minecarts, add the player's 'UUID' and the 'entityId' of the storage cart to the hashmap.
- then we check the distance while the cart is moving. if it's greater than 6, close it.
- if the person is legit and actually using the cart we just listen for the close packet and remove their uuid accordingly

### can it be bypassed?
- most likely if the 'duper' uses a different method i don't know of, most of the people doing this on your server won't think that far most likely

```
this is built for poseidon/uberbukkit (only tested on uberbukkit), a beta 1.7.3 server software.
```
