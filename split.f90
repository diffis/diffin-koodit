program sp
implicit none
integer :: v, j, io, max_kpl, i
real(kind=8), allocatable :: xs(:,:)
integer, allocatable :: kpl(:)
character(len=30) :: tiedosto
intrinsic maxval, adjustl

write(*,*) 'anna tiedosto'
read(*,*) tiedosto
open(10,file = tiedosto, status = 'old')

v = 0
do
  read(10, *, iostat=io) 
  if(io /= 0) exit
  v = v + 1
end do
allocate(xs(3,v),kpl(v))
rewind(10)
do j = 1,v
  read(10,*) kpl(j), xs(:,j)
end do
close(10)
max_kpl = maxval(kpl)

do j = 1,max_kpl
  write(tiedosto,*) j
  tiedosto = adjustl(tiedosto)
  open(10,file=tiedosto)
  do i = 1,v
    if(kpl(i) == j) write(10,*) xs(:,i)    
  end do
  close(10)
end do

end program sp
