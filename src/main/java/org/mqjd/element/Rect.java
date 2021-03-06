package org.mqjd.element;

import org.mqjd.common.*;
import org.mqjd.graphics.Graphics;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rect implements Element {
    private final Graphics graphics;
    private final Size size;
    private final Point point;
    private final BorderStyle borderStyle;
    private final Color color;

    public Rect(Graphics graphics, Point point, Size size) {
        this.graphics = graphics;
        this.point = point;
        this.size = size;
        this.borderStyle = BorderStyle.SINGLE_LINE;
        this.color = Color.WHITE;
    }

    public Rect(Graphics graphics, Point point, Size size, BorderStyle borderStyle, Color color) {
        this.graphics = graphics;
        this.point = point;
        this.size = size;
        this.borderStyle = borderStyle;
        this.color = color;
    }

    @Override
    public void draw() {
        String topEdge = String.format("%s%s%s", borderStyle.getNorthwest(), IntStream.range(0, size.getWidth() - 1)
            .mapToObj(v -> "").collect(Collectors.joining(borderStyle.getHorizontal())), borderStyle.getNortheast());
        String bottomEdge = String.format("%s%s%s", borderStyle.getSouthwest(), IntStream.range(0, size.getWidth() - 1)
            .mapToObj(v -> "").collect(Collectors.joining(borderStyle.getHorizontal())), borderStyle.getSoutheast());
        graphics.draw(new Border(graphics, point, topEdge, color));
        for (int i = 1; i < size.getHeight() - 1; i++) {
            graphics
                .draw(new Border(graphics, Point.of(point.getX(), i + point.getY()), borderStyle.getVertical(), color));
            graphics.draw(new Border(graphics, Point.of(point.getX() + size.getWidth() - 1, i + point.getY()),
                borderStyle.getVertical(), color));
        }
        graphics
            .draw(new Border(graphics, Point.of(point.getX(), point.getY() + size.getHeight() - 1), bottomEdge, color));
    }

    @Override
    public BoundingRect getBoundingRect() {
        return new BoundingRect(point, size);
    }
}
