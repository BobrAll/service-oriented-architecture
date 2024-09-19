import { AfterViewInit, Component } from '@angular/core'

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements AfterViewInit {

    ngAfterViewInit(): void {
        this.createGeometricAnimation();
    }

    createGeometricAnimation(): void {
        const canvas = document.getElementById('animatedBackground') as HTMLCanvasElement;
        const ctx = canvas.getContext('2d')!;
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;

        const points = [];
        const maxDistance = 150;

        class Point {
            x: number;
            y: number;
            vx: number;
            vy: number;

            constructor(x: number, y: number) {
                this.x = x;
                this.y = y;
                this.vx = Math.random() * 2 - 1;
                this.vy = Math.random() * 2 - 1;
            }

            draw(): void {
                ctx.beginPath();
                ctx.arc(this.x, this.y, 2, 0, Math.PI * 2);
                ctx.closePath();
                ctx.fill();
            }

            update(): void {
                this.x += this.vx;
                this.y += this.vy;

                if (this.x < 0 || this.x > canvas.width) this.vx *= -1;
                if (this.y < 0 || this.y > canvas.height) this.vy *= -1;
            }
        }


        for (let i = 0; i < 100; i++) {
            points.push(new Point(Math.random() * canvas.width, Math.random() * canvas.height));
        }

        function animate() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.fillStyle = '#FFFFFF';


            points.forEach((point: Point) => {
                point.update();
                point.draw();
            });


            for (let i = 0; i < points.length; i++) {
                for (let j = i + 1; j < points.length; j++) {
                    const dx = points[i].x - points[j].x;
                    const dy = points[i].y - points[j].y;
                    const distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance < maxDistance) {
                        ctx.beginPath();
                        ctx.moveTo(points[i].x, points[i].y);
                        ctx.lineTo(points[j].x, points[j].y);
                        ctx.strokeStyle = `rgba(255, 255, 255, ${1 - distance / maxDistance})`;
                        ctx.stroke();
                    }
                }
            }

            requestAnimationFrame(animate);
        }

        animate();


        window.addEventListener('resize', () => {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
        });
    }
}