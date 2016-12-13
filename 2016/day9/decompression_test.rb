require 'minitest/autorun'
require_relative 'decompressor'

class TestDecompressor < MiniTest::Unit::TestCase
  def test_decompress_advent
  	assert_equal "ADVENT" , Decompressor::decompress("ADVENT")
  end

  def test_onechar_repeat
  	assert_equal "ABBBBBC", Decompressor.decompress("A(1x5)BC")
  end

  def test_multichar_repeat
  	assert_equal "XYZXYZXYZ", Decompressor.decompress( "(3x3)XYZ" )
  end

  def test_multi_repeat
  	assert_equal "ABCBCDEFEFG", Decompressor.decompress( "A(2x2)BCD(2x2)EFG" )
  end

  def test_ignored_marker
  	assert_equal "(1x3)A", Decompressor.decompress( "(6x1)(1x3)A" ) 
  end

  def test_ignored_marker_again
  	assert_equal "X(3x3)ABC(3x3)ABCY", Decompressor.decompress( "X(8x2)(3x3)ABCY" ) 
  end
end
