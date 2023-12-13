<table>
    <thead>
        <tr>
            <th>Размер хипа</th>
            <th>Результат</th>
            <th>Время выполнения</th>
            <th>Оптимальный размер</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>256М</td>
            <td>java.lang.OutOfMemoryError</td>
            <td></td>
            <td></td>            
        </tr>
        <tr>
            <td>512М</td>
            <td></td>
            <td>18.419s</td>
            <td></td>            
        </tr>
        <tr>
            <td>1600М</td>
            <td></td>
            <td>15.273s</td>
            <td>да</td>            
        </tr>
        <tr>
            <td>2Г</td>
            <td></td>
            <td>15.110s</td>
            <td></td>            
        </tr>
        <tr>
            <td colspan=4><b>После модификации</b></td>
        </tr>
        <tr>
            <td>256М</td>
            <td></td>
            <td>4.404s</td>
            <td></td>
        </tr>
    </tbody>
</table>