program ring
implicit none
integer, parameter :: N = 30
real(kind=8), parameter :: dm = 0.0001_8, M = 1.0_8, pi = 4.0_8*atan(1.0_8), g = 4.0_8*pi*pi, r_min = 1.1_8
real(kind=8), parameter :: r_max = 1.5_8, dt = 0.005
!kun a = P = M = 1
real(kind=8) :: rs(3,N), vs(3,N), r, s, t, a(3,N)
integer :: v
intrinsic sqrt
t = 0.0_8
call init(rs, vs)
do
  rs = rs + 0.5_8*dt*vs
  a = acceleration(rs)
  vs = vs + dt*a
  rs = rs + 0.5_8*dt*vs
  do v = 1,N
    write(*,*) v, rs(:,v)
  end do
  t = t + dt
!  exit
  if(t > 2*pi) exit
end do

contains

function acceleration(r) result(acc)
implicit none
real(kind=8), intent(in) :: r(3,N)
real(kind=8) :: acc(3,N), d(N), diff(3,N)
integer :: i, j
intrinsic sqrt, dot_product
acc = 0.0_8
do j = 1,N !kappale, jonka kiihtyvyyttä lasketaan
  do i = 1,N
    diff(:,i) = r(:,i) - r(:,j)
  end do
  do i = 1,N
    d(i) = sqrt(dot_product(diff(:,i),diff(:,i)))
  end do
  do i = 1,N
    if(i == j) then
      acc(:,j) = acc(:,j) - g*M*r(:,j)/sqrt(dot_product(r(:,j),r(:,j)))**3
    else
      acc(:,j) = acc(:,j) - g*dm*diff(:,i)/d(i)**3
    end if
  end do
end do
return
end function acceleration

subroutine init(r, v)
implicit none
real(kind=8) :: r(3,N), v(3,N), x, y, s, a
integer :: i, j
intrinsic sin, cos, sqrt
do i = 1,N
  x = random(r_min,r_max)
  y = random(0.0_8,2.0_8*pi)
  a = random(-0.001_8,0.001_8)
  r(1,i) = x*cos(y)
  r(2,i) = x*sin(y)
  r(3,i) = random(-0.0005_8,0.0005_8)
  s = 2.0_8*pi/sqrt(x)
  v(1,i) = -s*sin(y)*cos(a)
  v(2,i) = s*cos(y)*cos(a)
  v(3,i) = s*sin(a)
  !write(*,*) 0.5*s*s - 4.0*pi*pi/x, -4.0*pi*pi*0.5_8/x
end do
return
end subroutine init

function random(a,b) result(r)
implicit none
real(kind=8), intent(in) :: a, b
real(kind=8) :: r
call random_number(r)
r = (b-a)*r + a
return
end function random


end program ring
