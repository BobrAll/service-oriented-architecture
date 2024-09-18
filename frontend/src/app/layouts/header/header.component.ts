import { AfterViewInit, Component } from '@angular/core'
import * as THREE from 'three';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements AfterViewInit {
    ngAfterViewInit(): void {
        this.createThreeJsAnimation();
    }

    createThreeJsAnimation(): void {
        const canvas = document.getElementById('headerCanvas') as HTMLCanvasElement;
        const renderer = new THREE.WebGLRenderer({ canvas, alpha: true });
        renderer.setSize(window.innerWidth, 80); // Высота header

        const scene = new THREE.Scene();
        const camera = new THREE.PerspectiveCamera(75, window.innerWidth / 100, 0.1, 800000);
        camera.position.z = 50;

        const geometry = new THREE.IcosahedronGeometry(100, 0);

        const pointMaterial = new THREE.PointsMaterial({
            color: 0xffffff,
            size: 10, // Размер точек
            transparent: true, // Позволяет управлять прозрачностью
        });

        const points = new THREE.Points(geometry, pointMaterial);
        scene.add(points);

        const lineMaterial = new THREE.LineBasicMaterial({
            color: 0xffffff,
            transparent: true,
            opacity: 0.7
        });

        const edges = new THREE.EdgesGeometry(geometry);
        const lineSegments = new THREE.LineSegments(edges, lineMaterial);
        scene.add(lineSegments);

        let time = 0;
        const animate = () => {
            requestAnimationFrame(animate);

            time += 0.07;

            pointMaterial.opacity = (Math.sin(time) + 0.5) / 2;

            points.rotation.x += 0.01;
            points.rotation.y += 0.01;
            lineSegments.rotation.x += 0.01;
            lineSegments.rotation.y += 0.01;

            renderer.render(scene, camera);
        };

        animate();
    }
}