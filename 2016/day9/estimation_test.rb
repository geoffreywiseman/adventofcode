require 'minitest/autorun'
require_relative 'decompressor'

class TestDecompressor < MiniTest::Unit::TestCase
  def test_multichar_repeat
  	assert_equal 9, Decompressor.estimate( "(3x3)XYZ" )
  end

  def test_nested_repeat
   	assert_equal 20, Decompressor.estimate( "X(8x2)(3x3)ABCY" )
  end

  def test_deeply_nested_repeat
    assert_equal 241920, Decompressor.estimate( "(27x12)(20x12)(13x14)(7x10)(1x12)A" )
  end

  def test_multi_repeat
    assert_equal 445, Decompressor.estimate( "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN" )
  end
end

#(27x12)(20x12)(13x14)(7x10)(1x12)A decompresses into a string of A repeated 241920 times.
#(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN becomes 445 characters long.