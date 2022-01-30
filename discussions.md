# Discussions

1. How did you deal with the constraint that you cannot change the "se.haleby.gildedrose.Item" class? Did you e.g. wrap it or mapped to/from it?  
2. If you update the tests for your new model, how does "make illegal states unrepresentable" affect your code? Are there some tests that are not needed?
3. Sulfuras - should it be updatable at all? Maybe it should, perhaps it's indeed updatable but the quantity doesn't change? This is a business decision and the code tells you that!