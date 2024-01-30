set xrange [-1.7:1.7]
set yrange [-1.7:1.7]
set size square
set terminal gif animate delay 10
set output 'animation.gif'
set key off
do for [ii=1:628] {
  plot '1' every ::ii::ii w p pt 7 lt rgb "blue", '2' every ::ii::ii w p pt 7 lt rgb "blue", '3' every ::ii::ii w p pt 7 lt rgb "blue", '4' every ::ii::ii w p pt 7 lt rgb "blue", '5' every ::ii::ii w p pt 7 lt rgb "blue", '6' every ::ii::ii w p pt 7 lt rgb "blue", '7' every ::ii::ii w p pt 7 lt rgb "blue", '8' every ::ii::ii w p pt 7 lt rgb "blue", '9' every ::ii::ii w p pt 7 lt rgb "blue", '10' every ::ii::ii w p pt 7 lt rgb "blue", '11' every ::ii::ii w p pt 7 lt rgb "blue", '12' every ::ii::ii w p pt 7 lt rgb "blue", '13' every ::ii::ii w p pt 7 lt rgb "blue", '14' every ::ii::ii w p pt 7 lt rgb "blue", '15' every ::ii::ii w p pt 7 lt rgb "blue", '16' every ::ii::ii w p pt 7 lt rgb "blue", '17' every ::ii::ii w p pt 7 lt rgb "blue", '18' every ::ii::ii w p pt 7 lt rgb "blue", '19' every ::ii::ii w p pt 7 lt rgb "blue", '20' every ::ii::ii w p pt 7 lt rgb "blue", '21' every ::ii::ii w p pt 7 lt rgb "blue", '22' every ::ii::ii w p pt 7 lt rgb "blue", '23' every ::ii::ii w p pt 7 lt rgb "blue", '24' every ::ii::ii w p pt 7 lt rgb "blue", '25' every ::ii::ii w p pt 7 lt rgb "blue", '26' every ::ii::ii w p pt 7 lt rgb "blue", '27' every ::ii::ii w p pt 7 lt rgb "blue", '28' every ::ii::ii w p pt 7 lt rgb "blue", '29' every ::ii::ii w p pt 7 lt rgb "blue", '30' every ::ii::ii w p pt 7 lt rgb "blue"
}
reset
set terminal qt
