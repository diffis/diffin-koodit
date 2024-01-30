#to create animation script in plt-file

print("set xrange [-1.7:1.7]")
print("set yrange [-1.7:1.7]")
print("set size square")
print("set terminal gif animate delay 10")
print("set output 'animation.gif'")
print("set key off")
print("do for [ii=1:628] {")

b1 = "  plot "
b2 = ''' every ::ii::ii w p pt 7 lt rgb "blue"'''
line = b1

for n in range(1,31):
  fil = str(n)
  line = line + "'" + fil + "'" + b2
  if n == 30: break
  line = line + ", "

print(line)

print("}")
print("reset")
