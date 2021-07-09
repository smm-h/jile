In order to have type inference, the type of any expression should be determineable before execution

An expression is a tree with method calls and operations as its non-leaf nodes, including the root, and their parameters as the leaves of this tree.

In Nile, each node is a verb, possibly extended by any number of adverbs. Verbs on their own are mere words with no definition other than a doc one. Here are some verb declarations:

```nile
verb become {
}
verb do {
verb mutate {
	verb add {
		adverb 
	}
	verb remove {}
	verb push {}
}
verb read {
	verb iterate {
		adverb -destructively
	}
}
}
```

----------------------------------------------------------------------

no number literals are allowed

verbs
	unparenthesized


------------------------------------------------------------------

having = owning | refering to = owning a copy of | owning a reference of

The only original owner is App, which can own, and thus create, anything
your "app" needs. It may even own more owners.

This means the only essentials are being, having [a reference of], being able to, and knowing how to do something.

is | has | can | does

is NOUN/NOUN+ADJECTIVE
has [article] NOUN/NOUN+ADJECTIVE [[named|called] NAME]
(can|is-able-to) VERB/VERB+ADVERB
does VERB/VERB+ADVERB

----------------------

"properties" are (internally) "owned" name->value mappings
whereas "attributes" are (externally) "attributed" mappings, by an external "attributer"
properties are self-attributes attributions

similarly, """CLASSES CAN BE EXTENDED WITH EXTERNAL METHODS""""

---------------------

quantity / magnitude / measuring unit / numbers / unbounded magnitudes vs bounded / scalar vs vector vs tensor / dimensions

are zero apples equal to zero oranges?

-------------------

A good teacher must not only understand what they are supposed to teach, but also remember how, through what processes and analogies, they came to understand it when they were like the person they are supposed to be teaching to.

That which is exoteric to me is esoteric to you. You have a relatively blank mind as I did once. But so much time has passed since then that I have forgotten how and through which processes I became what I am today. My inability to explain my thoughts and communicate my notions to you does not necessitate me not understanding them well enough. It only implies that my vocabulary has gradually stretched and shifted far away from the average origin point around which all people begin their journey as thinkers. So far that they barely share words.